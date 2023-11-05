package io.github.vino42.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import io.github.vino42.domain.DynamicHeader;
import io.github.vino42.domain.ExcelExportModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * =====================================================================================
 *
 * @Created :   2023/11/5 12:01
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class EasyExcelUtil {

    /**
     * 网络导出数据 自定义表头
     *
     * @param response
     * @param sheetName
     * @param fileName
     * @param datas
     * @param converters
     * @param headerClaz
     * @param customStratergies
     * @param <V>
     * @throws IOException
     */
    public static <V> void netExport(HttpServletResponse response,
                                     String fileName,
                                     String sheetName,
                                     List<V> datas,
                                     List<Converter> converters,
                                     Class<V> headerClaz,
                                     List<WriteHandler> customStratergies
    ) throws IOException {

        netExport(response, sheetName, fileName, datas, converters, headerClaz, null, ArrayUtil.toArray(customStratergies, WriteHandler.class));
        ;
    }

    /**
     * 网络导出数据自定义表头
     *
     * @param response
     * @param sheetName
     * @param fileName
     * @param datas
     * @param converters
     * @param headerClaz
     * @param faileResponseJson
     * @param customStratergies
     * @param <V>
     * @throws IOException
     */
    public static <V> void netExport(HttpServletResponse response,
                                     String fileName,
                                     String sheetName,
                                     List<V> datas,
                                     List<Converter> converters,
                                     Class<V> headerClaz,
                                     String faileResponseJson,
                                     List<WriteHandler> customStratergies
    ) throws IOException {

        netExport(response, sheetName, fileName, datas, converters, headerClaz, faileResponseJson, ArrayUtil.toArray(customStratergies, WriteHandler.class));
    }

    /**
     * 导出数据自定义表头
     *
     * @param response
     * @param sheetName
     * @param fileName
     * @param datas
     * @param converters
     * @param headerClaz
     * @param faileResponseJson
     * @param customStratergies
     * @param <V>
     * @throws IOException
     */
    public static <V> void netExport(HttpServletResponse response,
                                     String fileName,
                                     String sheetName,
                                     List<V> datas,
                                     List<Converter> converters,
                                     Class<V> headerClaz,
                                     String faileResponseJson,
                                     WriteHandler... customStratergies
    ) throws IOException {

        try {
            String encodeFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding(CharsetUtil.UTF_8);
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            ExcelWriterSheetBuilder builder = EasyExcel.write(response.getOutputStream(), headerClaz)
                    .head(ExcelExportModel.class)
                    .autoCloseStream(Boolean.TRUE).sheet(sheetName);
            if (ArrayUtil.isNotEmpty(customStratergies)) {
                Arrays.asList(customStratergies).forEach(builder::registerWriteHandler);
            }
            if (CollUtil.isNotEmpty(converters)) {
                converters.forEach(builder::registerConverter);
            }
            builder.doWrite(datas);
        } catch (Exception e) {
            if (StrUtil.isNotBlank(faileResponseJson)) {
                response.reset();
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
                response.getWriter().println(faileResponseJson);
            }
            throw e;
        }
    }

    /**
     * 导出数据动态表头
     *
     * @param response
     * @param sheetName
     * @param fileName
     * @param datas
     * @param converters
     * @param headers
     * @param faileResponseJson
     * @param customStratergies
     * @throws IOException
     */
    public static void netExport(HttpServletResponse response,
                                 String fileName,
                                 String sheetName,
                                 Collection<?> datas,
                                 List<Converter> converters,
                                 List<List<String>> headers,
                                 String faileResponseJson,
                                 WriteHandler... customStratergies
    ) throws IOException {

        try {
            String encodeFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding(CharsetUtil.UTF_8);
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + encodeFileName + ".xlsx");
            // 这里需要设置不关闭流
            ExcelWriterSheetBuilder builder = EasyExcel.write(response.getOutputStream())
                    .head(headers)
                    .autoCloseStream(Boolean.TRUE).sheet(sheetName);
            if (ArrayUtil.isNotEmpty(customStratergies)) {
                Arrays.asList(customStratergies).forEach(builder::registerWriteHandler);
            }
            if (CollUtil.isNotEmpty(converters)) {
                converters.forEach(builder::registerConverter);
            }
            builder.doWrite(datas);
        } catch (Exception e) {
            if (StrUtil.isNotBlank(faileResponseJson)) {
                response.reset();
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
                response.getWriter().println(faileResponseJson);
            }
            throw e;
        }
    }

    /**
     * 动态表头导出
     *
     * @param response          响应
     * @param sheetName         sheet名称
     * @param fileName          文件名
     * @param nameMap           动态表头集合
     * @param datas             数据集合
     * @param converters        转换器
     * @param faileResponseJson 失败原因
     * @param customStratergies 自定义策略
     * @throws IOException
     */
    public static void dynamicExport(HttpServletResponse response,
                                     String fileName,
                                     String sheetName,
                                     LinkedHashMap<String, DynamicHeader> nameMap,
                                     List<Map<String, Object>> datas,
                                     List<Converter> converters,
                                     String faileResponseJson,
                                     WriteHandler... customStratergies
    ) throws IOException {
        if (nameMap == null) {
            throw new RuntimeException("请填写好表头数据");
        }
        //数据转换
        int size = datas.size();
        List<List<String>> dataList = new ArrayList<>();
        IntStream.range(0, size).forEach(d -> dataList.add(new ArrayList<>()));
        //获取动态表头
        List<List<String>> head = nameMap.values().stream().map(dynamicHeader -> {
            String name = dynamicHeader.getName();
            return Collections.singletonList(name);
        }).toList();

        //数据重组
        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> map = datas.get(i);
            List<String> columns = dataList.get(i);
            for (Map.Entry<String, DynamicHeader> sortNameEntry : nameMap.entrySet()) {
                String key = sortNameEntry.getKey();
                Object value = map.get(key);
                columns.add(value != null ? String.valueOf(value) : sortNameEntry.getValue().getDefaultValue());
            }
        }
        netExport(response, sheetName, fileName, dataList, converters, head,
                faileResponseJson, customStratergies);
    }
}
