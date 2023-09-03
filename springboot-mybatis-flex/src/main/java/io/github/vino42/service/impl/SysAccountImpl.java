package io.github.vino42.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.domain.mapper.SysAccountMapper;
import io.github.vino42.service.SysAccountService;
import org.springframework.stereotype.Service;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023-09-03 23:42:29
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Copyright : vino
 * @Decription : 账号表 服务层。
 * @Since : 2023-09-03
 * =====================================================================================
 */
@Service
public class SysAccountImpl extends ServiceImpl<SysAccountMapper, SysAccountEntity> implements SysAccountService {

    @Override
    public boolean remove(QueryWrapper query) {
        return super.remove(query);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return super.removeByIds(ids);
    }

    @Override
    public boolean update(SysAccountEntity entity, QueryWrapper query) {
        return super.update(entity, query);
    }

    @Override
    public boolean updateById(SysAccountEntity entity, boolean ignoreNulls) {
        return super.updateById(entity, ignoreNulls);
    }

    @Override
    public boolean updateBatch(Collection<SysAccountEntity> entities, int batchSize) {
        return super.updateBatch(entities, batchSize);
    }

    @Override
    public SysAccountEntity getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public SysAccountEntity getOne(QueryWrapper query) {
        return super.getOne(query);
    }

    @Override
    public <R> R getOneAs(QueryWrapper query, Class<R> asType) {
        return super.getOneAs(query, asType);
    }

    @Override
    public List<SysAccountEntity> list(QueryWrapper query) {
        return super.list(query);
    }

    @Override
    public <R> List<R> listAs(QueryWrapper query, Class<R> asType) {
        return super.listAs(query, asType);
    }

    /**
     * @deprecated 无法通过注解进行缓存操作。
     */
    @Override
    @Deprecated
    public List<SysAccountEntity> listByIds(Collection<? extends Serializable> ids) {
        return super.listByIds(ids);
    }

    @Override
    public long count(QueryWrapper query) {
        return super.count(query);
    }

    @Override
    public <R> Page<R> pageAs(Page<R> page, QueryWrapper query, Class<R> asType) {
        return super.pageAs(page, query, asType);
    }

}
