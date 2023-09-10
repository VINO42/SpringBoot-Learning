package io.github.vino42.service;

import io.github.vino42.domain.entity.SysUserEntity;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/10 17:42:46
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Email : vino
 * @Copyright : vino
 * @Decription : 用户表 服务类
 * =====================================================================================
 */
public interface ISysUserService {

    List<SysUserEntity> list();
}
