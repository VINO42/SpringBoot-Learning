package io.github.vino42.domain.mapper;

import io.github.vino42.domain.entity.SysAccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/03 23:44:05
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Email : vino
 * @Copyright : vino
 * @Decription : 账号表 Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysAccountMapper extends BaseMapper<SysAccountEntity> {
}
