package io.github.vino42.controller;


import io.github.vino42.service.IUserService;
import io.github.vino42.support.ResultMapper;
import io.github.vino42.support.ServiceResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author vino
 * @since 2023-11-08
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/test")
    public ServiceResponseResult create() {

        userService.create();
        return ResultMapper.ok();
    }
}
