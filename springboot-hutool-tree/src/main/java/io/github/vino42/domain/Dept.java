package io.github.vino42.domain;

import lombok.Data;

/**
 * =====================================================================================
 *
 * @Created :   2023/6/13 21:43
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Data
public class Dept {
    private Long deptId;

    private Long parentId;
    private Integer level;
    private String name;
}
