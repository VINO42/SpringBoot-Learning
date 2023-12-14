package io.github.vino42.service.impl;

import io.github.vino42.domain.entity.Resource;
import io.github.vino42.domain.mapper.ResourceMapper;
import io.github.vino42.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vino
 * @since 2023-12-14
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Override
    public List<Resource> recursive(List<Integer> ids) {
        return this.baseMapper.recursive(ids);
    }
}
