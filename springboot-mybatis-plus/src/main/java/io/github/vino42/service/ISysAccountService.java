package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.InputDto;
import io.github.vino42.domain.entity.SysAccountEntity;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/03 23:44:05
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Email : vino
 * @Copyright : vino
 * @Decription : 账号表 服务类
 * =====================================================================================
 */
public interface ISysAccountService extends IService<SysAccountEntity> {

    boolean addInput(InputDto inputDto);

    void add(SysAccountEntity account);

    Object select();
}
