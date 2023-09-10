package io.github.vino42.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.vino42.domain.entity.SysUserEntity;
import io.github.vino42.domain.mapper.SysUserMapper;
import io.github.vino42.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/10 17:42:46
 * @Compiler :  jdk 17
 * @Email : vino
 * @Author :    vino
 * @Copyright : vino
 * @Decription : 用户表 服务实现类
 * =====================================================================================
 */
@Service
@DS("primary")
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List<SysUserEntity> list() {
        return sysUserMapper.list();
    }
}
