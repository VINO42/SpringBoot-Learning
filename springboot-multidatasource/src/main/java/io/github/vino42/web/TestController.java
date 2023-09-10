package io.github.vino42.web;

import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.domain.entity.SysUserEntity;
import io.github.vino42.service.ISysAccountService;
import io.github.vino42.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/10 18:19
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    ISysAccountService sysAccountService;

    @Autowired
    ISysUserService sysUserService;

    @RequestMapping("/a")
    public List<SysAccountEntity> testAccount(){
       List<SysAccountEntity> list= sysAccountService.list();
    return  list;
    }
    @RequestMapping("/u")
    public List<SysUserEntity> testUser(){
        List<SysUserEntity> list= sysUserService.list();
        return  list;
    }
}
