package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.domain.entity.User;
import io.github.vino42.domain.mapper.UserMapper;
import io.github.vino42.service.IUserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vino
 * @since 2023-11-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(User user) {
        int a=1/0;
        this.baseMapper.insert(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create() {
        User user =new User();
        user.setId(123);
        IUserService userService = (IUserService) AopContext.currentProxy();
        userService.save(user);
        User optById = userService.getById(123);
        System.out.println(optById);
    }

    @Override
    public void select() {
        User user =new User();
        user.setId(123);
    }
}
