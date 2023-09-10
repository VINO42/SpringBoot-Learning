package io.github.vino42.domain.mapper;

import io.github.vino42.domain.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/10 17:42:46
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Email : vino
 * @Copyright : vino
 * @Decription : 用户表 Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysUserMapper {
    List<SysUserEntity> list();

}
