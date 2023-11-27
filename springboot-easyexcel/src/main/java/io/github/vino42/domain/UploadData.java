package io.github.vino42.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import io.github.vino42.config.ObjectConverter;

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
public class UploadData {
    private Date createDate;
    private String age;
    private BigDecimal price;
    private String name;
    @ExcelProperty(value = "状态", index = 4, converter = ObjectConverter.class)
    private String status;
    @Override
    public String toString() {
        return "ExcelExportModel{" +
                "createDate=" + createDate +
                ", age='" + age + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
