package io.github.vino42.domain.mapper;

import io.github.vino42.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author vino
 * @since 2023-11-08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
