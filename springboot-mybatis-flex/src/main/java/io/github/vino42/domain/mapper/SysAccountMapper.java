package io.github.vino42.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import io.github.vino42.domain.entity.SysAccountEntity;

/**
 * =====================================================================================
 *
 * @Created :   2023-09-03 23:42:29
 * @Compiler :  jdk 17
 * @Author :     vino
 * @Copyright :  vino
 * @Decription : 账号表 映射层。
 * =====================================================================================
 */
@Mapper
public interface SysAccountMapper extends BaseMapper<SysAccountEntity> {

}
