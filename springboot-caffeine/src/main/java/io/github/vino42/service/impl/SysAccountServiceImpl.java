package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.domain.mapper.SysAccountMapper;
import io.github.vino42.service.ISysAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/11 21:40:48
 * @Compiler :  jdk 17
 * @Email : vino
 * @Author :    vino
 * @Copyright : vino
 * @Decription : 账号表 服务实现类
 * =====================================================================================
 */
@Service
@Slf4j
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccountEntity> implements ISysAccountService {
    /**
     * 注意这里添加完成后将 添加后的数据返回了 这样才能将数据添加到缓存中
     *
     * @param account
     * @return
     */
    @Override
    @CachePut(cacheNames = "cafe", key = "#account.id")
    public SysAccountEntity add(SysAccountEntity account) {
        this.save(account);
        log.info("add account:{}", account);
        return account;
    }

    @Override
    @Cacheable(cacheNames = "cafe", key = "#id")
    public SysAccountEntity getByAccountId(Long id) {
        log.info("getByName id:{}", id);
        SysAccountEntity byId = this.getById(id);

        return byId;
    }

    /**
     * 注意这里更新完成后将 更新后的数据返回了 这样才能更新缓存
     *
     * @param account
     * @return
     */
    @Override
    @CachePut(cacheNames = "cafe", key = "#account.id")
    public SysAccountEntity update(SysAccountEntity account) {
        log.info("update account:{}", account);
        boolean b = this.updateById(account);
        if (b) {
            return account;
        }
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "cafe", key = "#id")
    public boolean deleteByAccuntId(Long id) {
        log.info("del id:{}", id);
        return this.removeById(id);
    }
}
