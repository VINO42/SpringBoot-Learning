package io.github.vino42.mapping;

import io.netty.handler.codec.http.HttpMethod;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.util.*;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/31 23:04
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : 
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Component
public class RequestMappingInfoMetaInitilizer {
    @Autowired
    WebApplicationContext applicationContext;
    public static Map<String, RequestMappingInfoMeta> MAPPING_META = new HashMap<>();

    public void getMappings() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<org.springframework.web.servlet.mvc.method.RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PathPatternsRequestCondition p = info.getPathPatternsCondition();
            RequestMappingInfoMeta meta = new RequestMappingInfoMeta();
            for (String url : p.getDirectPaths()) {
                meta.setUrl(url);
            }
            String intern = method.getMethod().getDeclaringClass().getSimpleName().intern();
            //首字符小写
            String finalClassName = intern.replaceFirst(String.valueOf(intern.toCharArray()[0]), String.valueOf(intern.toCharArray()[0]).toLowerCase());
            meta.setClassName(finalClassName.intern()); // 类名
            meta.setMethod(method.getMethod());
            Set<String> methods = new HashSet<>();
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            if (methodsCondition.getMethods().size() != 0) {
                for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                    if (requestMethod.asHttpMethod().name().toLowerCase().equalsIgnoreCase(HttpMethod.POST.name())){
                        Class<?>[] parameterType = method.getMethod().getParameterTypes();
                        for (Class<?> aClass : parameterType) {
                            ClassLoader classLoader = aClass.getClassLoader();
                            if (classLoader!=null){
                               //非基础类型
                                meta.setParamaClaz(aClass);
                            }

                        }
                    }
                    methods.add(requestMethod.toString().toLowerCase().intern());
                }
            } else {
                Class<?>[] parameterType = method.getMethod().getParameterTypes();
                for (Class<?> aClass : parameterType) {
                    ClassLoader classLoader = aClass.getClassLoader();
                    if (classLoader!=null){
                        //非基础类型
                        meta.setParamaClaz(aClass);
                    }

                }
                methods.add(HttpMethod.POST.name().toLowerCase().intern());
                methods.add(HttpMethod.GET.name().toLowerCase().intern());

            }
            meta.setHttpMethods(methods);

            MAPPING_META.put(meta.getUrl().intern(), meta);
        }

    }

    @PostConstruct
    public void run() throws Exception {
        getMappings();
    }
}
