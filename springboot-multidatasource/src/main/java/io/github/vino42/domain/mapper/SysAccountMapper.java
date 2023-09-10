package io.github.vino42.domain.mapper;

import io.github.vino42.domain.entity.SysAccountEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/10 17:43:31
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Email : vino
 * @Copyright : vino
 * @Decription : 账号表 Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysAccountMapper {
    List<SysAccountEntity> list();
}
