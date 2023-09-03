package io.github.vino42.controller;

import com.mybatisflex.core.paginate.Page;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.service.SysAccountService;
import io.github.vino42.support.ResultMapper;
import io.github.vino42.support.ServiceResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


/**
 * =====================================================================================
 *
 * @Created :   2023-09-03 23:42:29
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Copyright :  vino
 * @Decription : 账号表 控制层。
 * =====================================================================================
 */
@RestController
@RequestMapping("/sysAccount")
public class SysAccountController {

    @Autowired
    private SysAccountService sysAccountService;

    /**
     * 添加账号表。
     *
     * @param sysAccountEntity 账号表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public ServiceResponseResult<Boolean> save(@RequestBody SysAccountEntity sysAccountEntity) {
        return ResultMapper.ok(sysAccountService.save(sysAccountEntity));
    }

    /**
     * 根据主键删除账号表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @PostMapping("/remove/{id}")
    public ServiceResponseResult<Boolean> remove(@PathVariable Serializable id) {
        return ResultMapper.ok(sysAccountService.removeById(id));
    }

    /**
     * 根据主键更新账号表。
     *
     * @param sysAccountEntity 账号表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PostMapping("/update")
    public ServiceResponseResult<Boolean> update(@RequestBody SysAccountEntity sysAccountEntity) {
        return ResultMapper.ok(sysAccountService.updateById(sysAccountEntity));
    }

    /**
     * 查询所有账号表。
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    public ServiceResponseResult<List<SysAccountEntity>> list() {
        return ResultMapper.ok(sysAccountService.list());
    }

    /**
     * 根据账号表主键获取详细信息。
     *
     * @param id 账号表主键
     * @return 账号表详情
     */
    @GetMapping("getInfo/{id}")
    public ServiceResponseResult<SysAccountEntity> getInfo(@PathVariable Serializable id) {
        return ResultMapper.ok(sysAccountService.getById(id));
    }

    /**
     * 分页查询账号表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public ServiceResponseResult<Page<SysAccountEntity>> page(Page<SysAccountEntity> page) {
        return ResultMapper.ok(sysAccountService.page(page));
    }

}
