package io.github.vino42.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * =====================================================================================
 *
 * @Created :   2023/09/10 17:42:46
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Copyright : vino
 * @Decription : 用户表
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUserEntity extends Model<SysUserEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户编号
     */
    private String code;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 1男2女0保密
     */
    private Integer sex;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 证件
     */
    private String idCard;

    /**
     * 证件类型
     */
    private String idCardType;

    /**
     * 联系地址
     */
    private String addr;

    /**
     * 省编码
     */
    private Integer provinceCode;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区编码
     */
    private String regionCode;

    /**
     * 省名称
     */
    private String province;

    /**
     * 市名称
     */
    private String city;

    /**
     * 区名称
     */
    private String region;

    /**
     * 用户状态0禁用1启用2删除
     */
    private Integer statu;

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
     * 版本号
     */
    private Long versionStamp;

    /**
     * 是否删除
     */
    private Integer isDel;


}
