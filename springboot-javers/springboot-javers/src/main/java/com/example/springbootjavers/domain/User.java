package com.example.springbootjavers.domain;

import org.javers.core.metamodel.annotation.DiffIgnore;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * =====================================================================================
 *
 * @Created :   2024/3/6 22:04
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

    private BigDecimal balance;

    private Double  height;

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", balance=" + balance +
                ", height=" + height +
                '}';
    }

    public User() {
    }

    public User(String name, int age, BigDecimal balance, Double height) {
        this.name = name;
        this.age = age;
        this.balance = balance;
        this.height = height;
    }
}
