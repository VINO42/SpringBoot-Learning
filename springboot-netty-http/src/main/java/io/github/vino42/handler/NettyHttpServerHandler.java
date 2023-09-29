package io.github.vino42.handler;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.vino42.mapping.RequestMappingInfoMeta;
import io.github.vino42.mapping.RequestMappingInfoMetaInitilizer;
import io.github.vino42.response.ServiceResult;
import io.github.vino42.serialize.GsonSerializerImpl;
import io.github.vino42.serialize.Serialize;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.net.HttpHeaders.KEEP_ALIVE;
import static org.springframework.http.HttpHeaders.CONNECTION;
import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.MediaType.*;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/31 20:58
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription : netty http handler
 * =====================================================================================
 */
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyHttpServerHandler.class);

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void channelRead0(ChannelHandlerContext context, HttpObject httpObject) {
        HttpRequest request;
        HttpHeaders headers;
        FullHttpRequest fullRequest;
        if (httpObject instanceof HttpRequest) {
            request = (HttpRequest) httpObject;
            headers = request.headers();
            String uri = request.uri();
            LOGGER.info("[request uri:{}]", uri);
            HttpMethod method = request.method();
            String realUri = uri.split("\\?")[0];
            boolean contains = RequestMappingInfoMetaInitilizer.MAPPING_META.containsKey(realUri);
            Object resultObj = null;
            if (contains) {
                //进行dispache转发
                RequestMappingInfoMeta meta = RequestMappingInfoMetaInitilizer.MAPPING_META.get(realUri);
                String className = meta.getClassName();
                Set<String> methods = meta.getHttpMethods();
                if (methods.contains(method.name().toLowerCase())) {
                    Object bean = SpringUtil.getBean(className);
                    if (HttpMethod.POST.name().intern().equals(method.name().intern())) {
                        fullRequest = (FullHttpRequest) httpObject;
                        Object o = handleWithContentType(headers, fullRequest, meta);
                        if (o != null) {
                            resultObj = ReflectUtil.invoke(bean, meta.getMethod(), o);
                        } else {
                            resultObj = ReflectUtil.invoke(bean, meta.getMethod());
                        }
                    } else if (HttpMethod.GET.name().intern().equalsIgnoreCase(method.name().intern())) {
                        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, StandardCharsets.UTF_8);
                        Map<String, List<String>> uriAttributes = queryDecoder.parameters();
                        resultObj = ReflectUtil.invoke(bean, meta.getMethod(), uriAttributes);
                    }

                } else {
                    illegalMethods(context);
                    return;
                }
                ServiceResult result;
                if (resultObj instanceof ServiceResult) {
                    result = (ServiceResult) resultObj;
                } else {
                    result = ServiceResult.ok(resultObj);
                }
                Serialize gsonSerializer = new GsonSerializerImpl();
                byte[] content = gsonSerializer.serialize(result);

                FullHttpResponse response = new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK,
                        Unpooled.wrappedBuffer(content));
                // 设置头信息
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, APPLICATION_JSON_VALUE);
                response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
                boolean keepAlive = HttpUtil.isKeepAlive(request);
                if (!keepAlive) {
                    context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    context.write(response);
                }
            } else {
                ServiceResult result = ServiceResult._404();
                Serialize gsonSerializer = new GsonSerializerImpl();
                byte[] content = gsonSerializer.serialize(result);

                FullHttpResponse response = new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK,
                        Unpooled.wrappedBuffer(content));
                // 设置头信息
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, APPLICATION_JSON_VALUE);
                // 将html write到客户端
                context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    private Object handleWithContentType(HttpHeaders headers, FullHttpRequest fullRequest, RequestMappingInfoMeta meta) {
        String typeStr = headers.get("Content-Type");
        if (typeStr == null) {
            typeStr = APPLICATION_JSON_VALUE;
        }
        String[] list = typeStr.split(";");
        String contentType = list[0];

        //可以使用HttpJsonDecoder
        if (APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType)) {
            String jsonStr = fullRequest.content().toString(StandardCharsets.UTF_8);
            if (meta.getParamaClaz() != null) {
                Object obj = GSON.fromJson(jsonStr, meta.getParamaClaz());
                return obj;
            } else {
                return null;
            }
//            for (Map.Entry<String, JsonElement> item : obj.entrySet()) {
//                LOGGER.info(item.getKey() + "=" + item.getValue().toString());
//            }
        } else if (APPLICATION_FORM_URLENCODED_VALUE.equals(contentType)) {
            //使用 QueryStringDecoder
            String jsonStr = fullRequest.content().toString(StandardCharsets.UTF_8);
            QueryStringDecoder queryDecoder = new QueryStringDecoder(jsonStr, false);
            Map<String, List<String>> uriAttributes = queryDecoder.parameters();
            for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                for (String attrVal : attr.getValue()) {
                    LOGGER.info(attr.getKey() + "=" + attrVal);
                }
            }
            return uriAttributes;
        } else if (MULTIPART_FORM_DATA_VALUE.equals(contentType)) {
            //TODO 用于文件上传
        } else {
            //do nothing...
        }
        return null;
    }


    /**
     * 处理异常
     *
     * @param ctx   上下文
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    private static void illegalMethods(ChannelHandlerContext context) {
        ServiceResult result = ServiceResult.illegalMethod();
        Serialize gsonSerializer = new GsonSerializerImpl();
        byte[] content = gsonSerializer.serialize(result);
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(content));
        // 设置头信息
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        // 将html write到客户端
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
