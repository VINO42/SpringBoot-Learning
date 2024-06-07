package io.github.vino42.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/03 23:44:05
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Copyright : vino
 * @Decription : 账号表
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_account")
public class SysAccountEntity extends Model<SysAccountEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**1
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户归属的组织
     */
    private Long orgId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账号状态 0禁用1启用
     */
    private Integer statu;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者id
     */
    private Long createBy;

    /**
     * 创建者名称
     */
    private String createName;

    /**
     * 创建者id
     */
    private Long updateBy;

    /**
     * 创建者名称
     */
    private String updateName;

    /**
     * 创建ip
     */
    private String createdIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastSigninTime;

    /**
     * 最后登录ip
     */
    private String lastSigninIp;

    /**
     * qq三方标识
     */
    private String qq;

    /**
     * 微信三方标识
     */
    private String wechat;

    /**
     * 钉钉三方标识
     */
    private String dingtalk;

    /**
     * 微博三方标识
     */
    private String weibo;

    /**
     * 百度三方标识
     */
    private String baidu;


}
