package io.github.vino42.domain;

import lombok.Data;

/**
 * =====================================================================================
 *
 * @Created :   2023/6/13 21:50
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Data
public class DeptVo {
    private Long deptId;

    private String name;

    private Long parentId;
    private Integer level;
    private DeptVo node;
}
