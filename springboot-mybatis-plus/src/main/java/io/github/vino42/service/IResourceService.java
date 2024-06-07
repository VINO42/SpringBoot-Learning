package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.ResourceDTO;
import io.github.vino42.domain.entity.Resource;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vino
 * @since 2023-12-14
 */
public interface IResourceService extends IService<Resource> {

    List<Resource> recursive(List<Integer> ids);
    List<ResourceDTO> recursive(String keyword);

}
