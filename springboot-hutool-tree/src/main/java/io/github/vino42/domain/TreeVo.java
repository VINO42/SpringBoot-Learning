package io.github.vino42.domain;

import cn.hutool.core.lang.tree.Tree;
import lombok.Data;

/**
 * =====================================================================================
 *
 * @Created :   2023/3/11 21:05
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Data
public class TreeVo<Integer> extends Tree<Integer> {

    private String name;

    private int age;

    private int count;


}
