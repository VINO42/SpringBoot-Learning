package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.entity.User;

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
