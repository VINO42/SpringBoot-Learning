package io.github.vino42.service;

import io.github.vino42.domain.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
