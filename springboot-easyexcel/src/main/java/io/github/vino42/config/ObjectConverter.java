package io.github.vino42.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.lang.reflect.Type;

/**
 * 对象转换器
 * 支持转换字段类型为自定义实体，List<实体>，List<String>，Map<String,实体>等
 *
 * @author wan.fei
 * @date 2023/08/26
 */
public class ObjectConverter implements Converter<Object> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 转换从excel中读取的数据为ExcelVO中定义的字段类型（自定义实体，List<实体>，List<String>，Map<String,实体>等）
     *
     * @param cellData            单元格数据
     * @param contentProperty     内容属性
     * @param globalConfiguration 全局配置
     * @return {@link Object}
     * @throws Exception 异常
     */
    @Override
    public Object convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        // 获取定义的实体字段的实际类型，包括泛型参数信息
//        Type genericType = contentProperty.getField().getGenericType();
        if (StrUtil.isBlank(stringValue)){
            return  null;
        }
        Integer code = StatusEnum.getCode(stringValue);

        return code;
    }

    /**
     * 转换数据为json字符串，写入到excel文件
     *
     * @param value               价值
     * @param contentProperty     内容属性
     * @param globalConfiguration 全局配置
     * @return {@link WriteCellData}<{@link ?}>
     * @throws Exception 异常
     */
    @Override
    public WriteCellData<?> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        if (value == null) {
            return new WriteCellData<String>("");
        }
        String desc = StatusEnum.getDesc((Convert.toInt(value)));
        return new WriteCellData<String>(desc);
    }
}