package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.domain.InputDto;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.domain.mapper.SysAccountMapper;
import io.github.vino42.service.ISysAccountService;
import io.github.vino42.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/03 23:44:05
 * @Compiler :  jdk 17
 * @Email : vino
 * @Author :    vino
 * @Copyright : vino
 * @Decription : 账号表 服务实现类
 * =====================================================================================
 */
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccountEntity> implements ISysAccountService {

    @Autowired
    @Lazy
    private ISysAccountService sysAccountService;
    @Autowired
    private IUserService userService;

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public boolean addInput(InputDto inputDto) {
        SysAccountEntity account = inputDto.getAccount();
        IntStream.range(0, 1).asLongStream().forEach(d -> {
            sysAccountService.add(account);
        });
        IntStream.range(0, 1).asLongStream().forEach(d -> {
            userService.add(inputDto.getUser());

        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysAccountEntity account) {
        this.baseMapper.insert(account);
    }
}
