package io.github.vino42.domain.entity;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * =====================================================================================
 *
 * @Created :   2023-09-03 23:42:29
 * @Compiler :  jdk 17
 * @Author :    vino
 * @Copyright : vino
 * @Decription : 账号表 表定义层。
 * @Since : 2023-09-03
 * =====================================================================================
 */
public class SysAccountTableDef extends TableDef {

    /**
     * 账号表
     */
    public static final SysAccountTableDef SYS_ACCOUNT_ENTITY = new SysAccountTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * qq三方标识
     */
    public final QueryColumn QQ = new QueryColumn(this, "qq");

    /**
     * 百度三方标识
     */
    public final QueryColumn BAIDU = new QueryColumn(this, "baidu");

    /**
     * 是否删除
     */
    public final QueryColumn IS_DEL = new QueryColumn(this, "is_del");

    /**
     * 用户归属的组织
     */
    public final QueryColumn ORG_ID = new QueryColumn(this, "org_id");

    /**
     * 账号状态 0禁用1启用
     */
    public final QueryColumn STATU = new QueryColumn(this, "statu");

    /**
     * 微博三方标识
     */
    public final QueryColumn WEIBO = new QueryColumn(this, "weibo");

    /**
     * 头像
     */
    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    /**
     * 手机号
     */
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");

    /**
     * 用户id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 微信三方标识
     */
    public final QueryColumn WECHAT = new QueryColumn(this, "wechat");

    /**
     * 创建者id
     */
    public final QueryColumn CREATE_BY = new QueryColumn(this, "create_by");

    /**
     * 钉钉三方标识
     */
    public final QueryColumn DINGTALK = new QueryColumn(this, "dingtalk");

    /**
     * 昵称
     */
    public final QueryColumn NICK_NAME = new QueryColumn(this, "nick_name");

    /**
     * 密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 创建者id
     */
    public final QueryColumn UPDATE_BY = new QueryColumn(this, "update_by");

    /**
     * 创建ip
     */
    public final QueryColumn CREATED_IP = new QueryColumn(this, "created_ip");

    /**
     * 创建者名称
     */
    public final QueryColumn CREATE_NAME = new QueryColumn(this, "create_name");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建者名称
     */
    public final QueryColumn UPDATE_NAME = new QueryColumn(this, "update_name");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 最后登录ip
     */
    public final QueryColumn LAST_SIGNIN_IP = new QueryColumn(this, "last_signin_ip");

    /**
     * 最后登录时间
     */
    public final QueryColumn LAST_SIGNIN_TIME = new QueryColumn(this, "last_signin_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ORG_ID, USER_ID, MOBILE, PASSWORD, AVATAR, NICK_NAME, STATU, IS_DEL, CREATE_TIME, UPDATE_TIME, CREATE_BY, CREATE_NAME, UPDATE_BY, UPDATE_NAME, CREATED_IP, LAST_SIGNIN_TIME, LAST_SIGNIN_IP, QQ, WECHAT, DINGTALK, WEIBO, BAIDU};

    public SysAccountTableDef() {
        super("", "sys_account");
    }

}
