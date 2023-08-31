package io.github.vino42.response;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/31 23:26
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : 
 * @Copyright : VINO
 * @Decription : 自定result响应封装
 * =====================================================================================
 */

import lombok.Data;

@Data
public class ServiceResult<T> {
    private int code;

    private String msg;

    private T data;

    public static ServiceResult illegalMethod() {
        ServiceResult result = new ServiceResult();
        result.setCode(405);
        result.setData("mehod illegal");
        result.setMsg("请求方法非法");
        return result;
    }
    public static ServiceResult ok(Object o) {
        ServiceResult result = new ServiceResult();
        result.setCode(200);
        result.setData(o);
        result.setMsg("ok");
        return result;
    }
    public static ServiceResult _404() {
        ServiceResult result = new ServiceResult();
        result.setCode(404);
        result.setData("请求地址不存在");
        result.setMsg("请求地址不存在");
        return result;
    }
}
