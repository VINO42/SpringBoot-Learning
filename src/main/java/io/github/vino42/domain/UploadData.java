package io.github.vino42.domain;

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
public class UploadData {
    private Date createDate;
    private String age;
    private BigDecimal price;
    private String name;


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


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
