package io.github.vino42.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * =====================================================================================
 *
 * @Created :   2023-09-03 23:42:29
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Copyright : vino
 * @Decription : 账号表 实体类。
 * @Since:  2023-09-03
 * =====================================================================================
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table(value = "sys_account")
public class SysAccountEntity extends Model<SysAccountEntity>  implements Serializable {


    /**
     * 主键
     */
    @Id(keyType = KeyType.None)
    private BigInteger id;

    /**
     * 用户归属的组织
     */
    private Long orgId;

    /**
     * 用户id
     */
    private BigInteger userId;

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
    private Long statu;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
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
    private BigInteger updateBy;

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
    private byte[] baidu;

}
