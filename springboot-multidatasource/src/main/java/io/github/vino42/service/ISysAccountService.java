package io.github.vino42.service;

import io.github.vino42.domain.entity.SysAccountEntity;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/10 17:43:31
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Email : vino
 * @Copyright : vino
 * @Decription : 账号表 服务类
 * =====================================================================================
 */
public interface ISysAccountService {

    List<SysAccountEntity> list();
}
