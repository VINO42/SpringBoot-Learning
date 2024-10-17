package io.github.vino42.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.service.ISysAccountService;
import io.github.vino42.support.ResultMapper;
import io.github.vino42.support.ServiceResponseResult;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/03 23:44:05
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Copyright : vino
 * @Decription : 账号表 控制器
 * =====================================================================================
 */
@RestController
@RequestMapping("/sysAccount")
public class SysAccountController {

    @Autowired
    private ISysAccountService sysAccountService;


    @GetMapping(value = "/page")
    public ServiceResponseResult<IPage> getSysAccountEntityPage(Page<SysAccountEntity> page, SysAccountEntity sysAccount) {
        return ResultMapper.ok(sysAccountService.page(page, Wrappers.query(sysAccount)));
    }


    @PostMapping(value = "/add")
    public ServiceResponseResult create(@RequestBody SysAccountEntity sysAccount) {
        return ResultMapper.ok(sysAccountService.save(sysAccount));
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult update(@RequestBody SysAccountEntity sysAccount) {
        return ResultMapper.ok(sysAccountService.updateById(sysAccount));
    }


    @PostMapping(value = "/delete/{id}")
    public ServiceResponseResult delete(@PathVariable Long id) {
        return ResultMapper.ok(sysAccountService.removeById(id));
    }

    @GetMapping(value = "/select")
    public ServiceResponseResult select() {
        return ResultMapper.ok(sysAccountService.select());
    }

    @PostMapping(value = "/selectp")
    public ServiceResponseResult select(@RequestBody SysAccountEntity sysAccountEntity) {
        return ResultMapper.ok(sysAccountService.select(sysAccountEntity));
    }

    @GetMapping(value = "/selectd")
    public ServiceResponseResult selectd() {
        return ResultMapper.ok(sysAccountService.selectd());
    }

    @GetMapping(value = "/selecte")
    public ServiceResponseResult selecte() {
        sysAccountService.selecte();
        return ResultMapper.ok();
    }

    @GetMapping(value = "/selectf")
    public ServiceResponseResult selectf() throws InterruptedException {
        sysAccountService.selectf();
        return ResultMapper.ok();
    }

    @GetMapping(value = "/saveA")
    public ServiceResponseResult saveA() throws InterruptedException {
        SysAccountEntity a = new SysAccountEntity();
        a.setId(1L);
        a.setOrgId(0L);
        a.setUserId(0L);
        a.setMobile("");
        a.setPassword("");
        a.setAvatar("");
        a.setNickName("");
        a.setIsDel(0);


        sysAccountService.saveA(a);
        return ResultMapper.ok();
    }

    @GetMapping(value = "/saveB")
    public ServiceResponseResult saveB() throws InterruptedException {

        SysAccountEntity byId = sysAccountService.getById(1);
        sysAccountService.updateById(byId);
        return ResultMapper.ok();
    }

    @GetMapping("/getParams")
    public String getParams(String a, String b) {
        System.out.println("a: " + a + " , b: " + b);

        return "get success";
    }

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @PostMapping("/postTest")
    public String postTest(HttpServletRequest request, HttpServletResponse response, String age, String name) {
        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        AsyncContext asyncContext;
        if (contentCachingRequestWrapper.isAsyncStarted()) {
            System.out.println("contentCachingRequestWrapper.isAsyncStarted");
            asyncContext = contentCachingRequestWrapper.getAsyncContext();
        } else {
            asyncContext = contentCachingRequestWrapper.startAsync(request, response);
        }
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                String age2 = contentCachingRequestWrapper.getParameter("age");
                String name2 = contentCachingRequestWrapper.getParameter("name");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String age3 = request.getParameter("age");
                String name3 = request.getParameter("name");
                System.out.println("age1: " + age + " , name1: " + name + " , age2: " + age2 + " , name2: " + name2 + " , age3: " + age3 + " , name3: " + name3);
                asyncContext.complete();
            }
        }, executorService);
        return "post success";
    }

}
