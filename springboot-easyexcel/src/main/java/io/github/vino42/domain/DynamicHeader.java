package io.github.vino42.domain;

/**
 * =====================================================================================
 *
 * @Created :   2023/11/5 12:13
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */

public class DynamicHeader {

    private String name;

    private String defaultValue;

    private Integer defaultWidth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getDefaultWidth() {
        return defaultWidth;
    }

    public void setDefaultWidth(Integer defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public DynamicHeader(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.defaultWidth = name.length() + 4;
    }
}
