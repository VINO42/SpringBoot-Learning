package io.github.vino42.service;

import io.github.vino42.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vino
 * @since 2023-11-08
 */
public interface IUserService extends IService<User> {

    void add(User user);

    void create();

    void select();
}
