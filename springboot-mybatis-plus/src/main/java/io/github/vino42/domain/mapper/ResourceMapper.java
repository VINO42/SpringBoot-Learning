package io.github.vino42.domain.mapper;

import io.github.vino42.domain.ResourceDTO;
import io.github.vino42.domain.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author vino
 * @since 2023-12-14
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    List<Resource> recursive(@Param("ids") List<Integer> ids);

    List<ResourceDTO> recursiveKeyWord(@Param("keyword") String keyword);
}
