package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.entity.SysAccountEntity;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/11 21:40:48
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Email : vino
 * @Copyright : vino
 * @Decription : 账号表 服务类
 * =====================================================================================
 */
public interface ISysAccountService extends IService<SysAccountEntity> {
    SysAccountEntity add(SysAccountEntity account);


    SysAccountEntity update(SysAccountEntity account);

    boolean deleteByAccuntId(Long account);

    SysAccountEntity getByAccountId(Long id);
}
