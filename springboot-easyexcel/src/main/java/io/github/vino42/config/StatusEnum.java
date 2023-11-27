package io.github.vino42.config;

import java.util.Arrays;
import java.util.Optional;

/**
 * =====================================================================================
 *
 * @Created :   2023/11/27 22:42
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public enum StatusEnum {
    ENABLE(1, "可用"),
    DISABLED(0, "禁用");

    private Integer code;
    private String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDesc(Integer code) {
        StatusEnum[] values = StatusEnum.values();
        Optional<StatusEnum> statusEnum = Arrays.asList(values).stream().filter(d -> d.getCode().equals(code)).findFirst();
        StatusEnum statusEnum1 = statusEnum.orElse(null);
        if (statusEnum1 == null) {
            return null;
        } else {
            return statusEnum1.getDesc();
        }
    }

    public static Integer getCode(String desc) {
        StatusEnum[] values = StatusEnum.values();
        Optional<StatusEnum> statusEnum = Arrays.asList(values).stream().filter(d -> d.getDesc().equals(desc)).findFirst();
        StatusEnum statusEnum1 = statusEnum.orElse(null);
        if (statusEnum1 == null) {
            return null;
        } else {
            return statusEnum1.getCode();
        }
    }

    public static StatusEnum getInstance(Integer code) {
        StatusEnum[] values = StatusEnum.values();
        Optional<StatusEnum> statusEnum = Arrays.asList(values).stream().filter(d -> d.getCode().equals(code)).findFirst();
        StatusEnum statusEnum1 = statusEnum.orElse(null);
        return statusEnum1;
    }

    public static StatusEnum getInstance(String code) {
        StatusEnum[] values = StatusEnum.values();
        Optional<StatusEnum> statusEnum = Arrays.asList(values).stream().filter(d -> d.getDesc().equals(code)).findFirst();
        StatusEnum statusEnum1 = statusEnum.orElse(null);
        return statusEnum1;
    }
}
