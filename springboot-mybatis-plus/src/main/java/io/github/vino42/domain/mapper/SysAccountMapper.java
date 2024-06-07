package io.github.vino42.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.SysAccountDTO;
import io.github.vino42.domain.entity.SysAccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    List<SysAccountDTO> selectListCon(@Param("datas") List<SysAccountDTO> list);

    List<SysAccountEntity> selectListP(@Param("p") SysAccountEntity p);
}
