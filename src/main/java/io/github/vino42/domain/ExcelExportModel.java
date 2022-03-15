package io.github.vino42.domain;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/15 20:51
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class ExcelExportModel implements Serializable {
    @ExcelProperty(value = "创建日期", index = 0)
    private Date createDate;
    @ExcelProperty(value = "年龄", index = 1)
    private String age;
    @ExcelProperty(value = "价格", index = 2)
    private BigDecimal price;
    @ExcelProperty(value = "名称", index = 3)
    private String name;

    public ExcelExportModel(Date createDate, String age, BigDecimal price, String name) {
        this.createDate = createDate;
        this.age = age;
        this.price = price;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ExcelExportModel{" +
                "createDate=" + createDate +
                ", age='" + age + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
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
                BigDecimal.valueOf(RandomUtil.randomDouble()), String.valueOf(RandomUtil.randomChinese())
        );
        return data;

    }
}
