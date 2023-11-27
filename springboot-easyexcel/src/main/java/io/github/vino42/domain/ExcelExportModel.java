package io.github.vino42.domain;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.github.vino42.config.ObjectConverter;
import io.github.vino42.config.StatusEnum;
import io.github.vino42.config.StatusEnumConverter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/15 20:51
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class ExcelExportModel implements Serializable {
    @ExcelProperty(value = "创建日期")
    private Date createDate;
    @ExcelProperty(value = "年龄")
    private String age;
    @ExcelProperty(value = "价格")
    private BigDecimal price;
    @ExcelProperty(value = "名称")
    @ColumnWidth(40)
    private String name;
    @ExcelProperty(value = "状态", converter = ObjectConverter.class)
    private Integer status;

    @ExcelProperty(value = "状态1", converter = StatusEnumConverter.class)
    private StatusEnum status1;

    public ExcelExportModel() {
    }

    public StatusEnum getStatus1() {
        return status1;
    }

    public void setStatus1(StatusEnum status1) {
        this.status1 = status1;
    }

    public ExcelExportModel(Date createDate, String age, BigDecimal price, String name, Integer status, StatusEnum status1) {
        this.createDate = createDate;
        this.age = age;
        this.price = price;
        this.name = name;
        this.status = status;
        this.status1 = status1;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ExcelExportModel{" +
                "createDate=" + createDate +
                ", age='" + age + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", status1=" + status1 +
                '}';
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ExcelExportModel getData() {
        ExcelExportModel data = new ExcelExportModel(new Date(), String.valueOf(RandomUtil.randomInt()),
                BigDecimal.valueOf(RandomUtil.randomDouble()), String.valueOf(RandomUtil.randomChinese()) + RandomUtil.randomChinese() + RandomUtil.randomChinese() + RandomUtil.randomChinese()
                , RandomUtil.randomInt(2), StatusEnum.getInstance(RandomUtil.randomInt(2))
        );
        return data;

    }
}
