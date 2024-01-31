package com.example.domain;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2024/1/31 22:14
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class User implements Serializable {

    private String name;

    private Integer age;

    private String attr1;

    private String atrr2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAtrr2() {
        return atrr2;
    }

    public void setAtrr2(String atrr2) {
        this.atrr2 = atrr2;
    }
}
