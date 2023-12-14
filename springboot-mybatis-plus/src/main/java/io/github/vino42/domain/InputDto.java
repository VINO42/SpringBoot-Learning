package io.github.vino42.domain;

import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.domain.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2023/11/8 22:09
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Data
public class InputDto implements Serializable {
    private SysAccountEntity account;
    private User user;
}
