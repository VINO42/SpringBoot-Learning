package io.github.vino42.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.service.ISysAccountService;
import io.github.vino42.support.ResultMapper;
import io.github.vino42.support.ServiceResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
