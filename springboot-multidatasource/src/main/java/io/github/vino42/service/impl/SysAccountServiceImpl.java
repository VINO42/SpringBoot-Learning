package io.github.vino42.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.domain.mapper.SysAccountMapper;
import io.github.vino42.service.ISysAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/10 17:43:31
 * @Compiler :  jdk 17
 * @Email : vino
 * @Author :    vino
 * @Copyright : vino
 * @Decription : 账号表 服务实现类
 * =====================================================================================
 */
@Service
@DS("second")
public class SysAccountServiceImpl implements ISysAccountService {
    @Autowired
    SysAccountMapper sysAccountMapper;

    @Override
    public List<SysAccountEntity> list() {
        return sysAccountMapper.list();
    }
}
