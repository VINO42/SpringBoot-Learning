package io.github.vino42.constant;

import lombok.Getter;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/2 1:54
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public enum ServiceStatusEnum {
    /**
     * 服务下线
     */
    DOWN(0),
    /**
     * 服务上线
     */
    UP(1);

    @Getter
    private Integer type;

    ServiceStatusEnum(Integer type) {
        this.type = type;
    }
}
