package io.github.vino42.config;

import lombok.Data;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2024/9/11 23:24
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Data
public class User implements Serializable {

    private String name;

    private String nickName;
}
