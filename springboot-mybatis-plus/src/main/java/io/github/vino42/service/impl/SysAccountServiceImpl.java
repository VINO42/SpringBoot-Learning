package io.github.vino42.service.impl;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import io.github.vino42.domain.InputDto;
import io.github.vino42.domain.SysAccountDTO;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.domain.entity.User;
import io.github.vino42.domain.mapper.SysAccountMapper;
import io.github.vino42.service.ISysAccountService;
import io.github.vino42.service.IUserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static cn.hutool.core.text.StrPool.COMMA;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/03 23:44:05
 * @Compiler :  jdk 17
 * @Email : vino
 * @Author :    vino
 * @Copyright : vino
 * @Decription : 账号表 服务实现类
 * =====================================================================================
 */
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccountEntity> implements ISysAccountService {

    @Autowired
    @Lazy
    private ISysAccountService sysAccountService;
    @Autowired
    private IUserService userService;

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public boolean addInput(InputDto inputDto) {
        SysAccountEntity account = inputDto.getAccount();
        IntStream.range(0, 1).asLongStream().forEach(d -> {
            sysAccountService.add(account);
        });
        IntStream.range(0, 1).asLongStream().forEach(d -> {
            userService.add(inputDto.getUser());

        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysAccountEntity account) {
        this.baseMapper.insert(account);
    }

    @Override
    public Object select() {
        SysAccountDTO accountEntity1 = new SysAccountDTO();
        accountEntity1.setMobile("3454");
        accountEntity1.setPassword("1");
        accountEntity1.setName("a");
        accountEntity1.setAge("1");
        SysAccountDTO accountEntity2 = new SysAccountDTO();
        accountEntity2.setMobile("1888888888");
        accountEntity2.setPassword("345");
        accountEntity2.setName("b");
        accountEntity2.setAge("2");
        List<SysAccountDTO> list = Lists.newArrayList();
        list.add(accountEntity1);
        list.add(accountEntity2);
        List<SysAccountDTO> sysAccountDTOS = this.baseMapper.selectListCon(list);
        System.out.println(JSONUtil.toJsonStr(sysAccountDTOS));
        return sysAccountDTOS;
    }

    @Override
    public List<SysAccountEntity> selectd() {
        List<String> list = Arrays.asList("id", "org_id", "user_id", "mobile", "password", "avatar", "nick_name", "statu", "is_del");
        String join = Joiner.on(COMMA).join(list);
//        List<SysAccountEntity> result=  this.baseMapper.selectd(join);
        List<SysAccountEntity> result = this.baseMapper.selectf(list);

        return result;
    }

    @Override
    public void selecte() {
        SysAccountDTO accountEntity1 = new SysAccountDTO();
        User user = new User();
        user.setId(123131);
        accountEntity1.setUser(user);
        accountEntity1.setMobile("3454");
        accountEntity1.setPassword("1");
        accountEntity1.setName("a");
        accountEntity1.setAge("1");
        SysAccountDTO accountEntity2 = new SysAccountDTO();
        accountEntity2.setMobile("1888888888");
        accountEntity2.setPassword("345");
        accountEntity2.setName("b");
        accountEntity2.setAge("2");
        User user1 = new User();
        user1.setId(456);
        accountEntity2.setUser(user);
        List<SysAccountDTO> list = Lists.newArrayList();
        list.add(accountEntity1);
        list.add(accountEntity2);
        changeUser(list);
        System.out.println(JSONUtil.toJsonPrettyStr(list));
    }

    @Autowired
    private DataSourceTransactionManager transactionManager;

    /**
     * 多线程的事务处理方式，
     * @throws InterruptedException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void selectf() throws InterruptedException {
        DefaultTransactionDefinition dd = new DefaultTransactionDefinition();
        TransactionStatus mainTransStatus = transactionManager.getTransaction(dd);
        CountDownLatch c1 = new CountDownLatch(12);
        AtomicBoolean a1 = new AtomicBoolean(true);

        try {
            SysAccountEntity accountEntity2 = new SysAccountEntity();
            accountEntity2.setMobile("3454");
            accountEntity2.setPassword("1");
            accountEntity2.setUserId(RandomUtil.randomLong(1200));
            this.baseMapper.insert(accountEntity2);
            StopWatch s1 = new StopWatch();
            s1.start();
            List<SysAccountEntity> entities = Lists.newArrayList();
            for (int i = 0; i < 1200; i++) {
                SysAccountEntity accountEntity1 = new SysAccountEntity();
                accountEntity1.setMobile("3454" + i);
                accountEntity1.setPassword("1" + i);
                accountEntity1.setUserId(RandomUtil.randomLong(1200));
                entities.add(accountEntity1);
            }
            ISysAccountService service = (ISysAccountService) AopContext.currentProxy();
            List<List<SysAccountEntity>> partition = Lists.partition(entities, 100);
            ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(12, 12);
            for (List<SysAccountEntity> sysAccountEntities : partition) {
                threadPoolExecutor.execute(() -> {
                    DefaultTransactionDefinition subdd = new DefaultTransactionDefinition();
                    TransactionStatus subTransStatus = transactionManager.getTransaction(subdd);

                    try {
                        StopWatch ss = new StopWatch();
                        ss.start();
                        service.saveOrUpdateBatch(sysAccountEntities);
                        ss.stop();
                        System.out.println("ss耗时" + ss.getTotalTimeMillis());

                        for (SysAccountEntity entity : sysAccountEntities) {
                            entity.setAvatar(RandomUtil.randomString(10));
                        }
                        System.out.println(JSONUtil.toJsonPrettyStr(sysAccountEntities));
                        service.updateBatchById(sysAccountEntities);
                        int i = 1 / 0;
                    } catch (Exception e) {
                        log.error("e", e);
                        e.printStackTrace();
                        a1.set(false);
                    } finally {
                        c1.countDown();
                        try {
                            c1.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (a1.get()) {
                            transactionManager.commit(subTransStatus);
                        } else {
                            transactionManager.rollback(subTransStatus);
                        }
                        System.out.println("------------------" + Thread.currentThread().getName());
                    }

                });
            }
            System.out.println(c1.toString());
//            int i = 1 / 0;
            s1.stop();
            System.out.println("s1耗时" + s1.getTotalTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
            a1.set(false);
        } finally {
            System.out.println(c1.toString());
            c1.await();
            if (a1.get()) {
                System.out.println("------------------ commit" + Thread.currentThread().getName());
                transactionManager.commit(mainTransStatus);
            } else {
                System.out.println("------------------ rollback " + Thread.currentThread().getName());
                transactionManager.rollback(mainTransStatus);


            }
        }
    }

    private void changeUser(List<SysAccountDTO> list) {
        for (SysAccountDTO sysAccountDTO : list) {
            User user = sysAccountDTO.getUser();
            user.setId(0001);
        }
    }

    @Override
    public List<SysAccountEntity> select(SysAccountEntity p) {
        return this.baseMapper.selectListP(p);
    }
}
