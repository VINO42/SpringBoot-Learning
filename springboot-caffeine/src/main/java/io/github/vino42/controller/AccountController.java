package io.github.vino42.controller;

import cn.hutool.core.util.IdUtil;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.service.ISysAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/11 21:33
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RequestMapping("/")
@RestController
@Slf4j
public class AccountController {
    @Autowired
    ISysAccountService accountService;

    @RequestMapping("/add")
    public SysAccountEntity testAdd(@RequestBody SysAccountEntity account) {
        account.setId(IdUtil.getSnowflakeNextId());
        log.info("[ gened accountId:{}]", account.getId());
        return accountService.add(account);
    }

    @RequestMapping("/get")
    public SysAccountEntity testGet(@RequestParam("id") Long id) {
        return accountService.getByAccountId(id);
    }

    @RequestMapping("/update")
    public SysAccountEntity testUpdate(@RequestBody SysAccountEntity account) {
        return accountService.update(account);
    }

    @RequestMapping("/del")
    public boolean testDel(@RequestParam("id") Long accountId) {
        return accountService.deleteByAccuntId(accountId);
    }
}
