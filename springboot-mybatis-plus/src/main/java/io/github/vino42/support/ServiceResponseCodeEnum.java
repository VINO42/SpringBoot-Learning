package io.github.vino42.support;

import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static java.util.Arrays.asList;

public enum ServiceResponseCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200000, "ok", "成功", Level.INFO, HttpStatus.OK),


    BAD_REQUEST(400000, "bad request", "错误请求", Level.ERROR, HttpStatus.BAD_REQUEST),

    ILLEGAL_ARGS(400001, "Illegal argument", "参数错误", Level.ERROR, HttpStatus.BAD_REQUEST),

    /**
     * 参数校验不通过
     */
    PARAM_NOT_VALID(400004, "Parameter not valid. for %s", "参数校验不通过", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 参数校验不通过 为空
     */
    PARAM_BLANK(400005, "The required parameter %s is blank.", "参数校验不通过,参数存在空参数", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 参数校验 超界限
     */
    PARAM_OUT_RANGE(400006, "The value of parameter %s is not in the right range.", "参数校验不通过,超界限", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 表单异常
     */
    PARAM_FORMAT_INVALID(400007, "The format of parameter %s is not correct.", "表单提交异常", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 分页请求数据过大
     */
    PARAM_PAGE_SETTING_INVALID(400008, "Return message is too long, please resize page and retry.", "分页请求数据过大", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 参数不支持
     */
    PARAM_NOT_SUPPORT(400009, "The parameter(%s) not supported.", "参数不支持", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 请求内容过长
     */
    PARAM_CONTENT_TOO_LONG(400010, "The parameter(%s) content is out of limit.", "请求内容过长", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 请求不可读
     */
    PARAM_BODY_NOT_READABLE(400011, "HttpMessageNotReadable", "请求不可读", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 参数类型不匹配
     */
    PARAM_TYPE_NOT_MATCH(400012, "MethodArgumentTypeMismatch. The value of %s(%s) resolved to %s fail.", "参数类型不匹配", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 非multipart request
     */
    MULTIPART_INVALID(400013, "Request is not a validate multipart request, please check request or file size.", "非multipart 请求", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 无效的账号或密码
     */
    ACCOUNT_OR_PWD_INVALID(400014, "invalid account or password .", "无效的账号或密码", Level.ERROR, HttpStatus.BAD_REQUEST),

    /**
     * 无效的短信验证码
     */
    SMS_CODE_INVALID(400015, "invalid sms code .", "无效短信验证码", Level.ERROR, HttpStatus.BAD_REQUEST),

    /**
     * 无效的图形验证码
     */
    CAPTCHA_CODE_INVALID(400016, "invalid captcha code .", "无效的图形验证码", Level.ERROR, HttpStatus.BAD_REQUEST),


    CONTENT_TYPE_NOT_SUPPORT(400017, "CONTENT_TYPE_NOT_SUPPORT", "不支持的请求", Level.INFO, HttpStatus.BAD_REQUEST),

    METHOD_ARGUMENT_NOT_VALID(400018, "METHOD_ARGUMENT_NOT_VALID", "接口参数校验失败，参数使用错误或者未接收到参数"),

    CAPTCHA_CODE_EXPIRED(400019, "  captcha code  expired .", "图形验证码已过期", Level.ERROR, HttpStatus.BAD_REQUEST),

    CAPTCHA_CODE_NOT_FIT(400020, "  captcha code  not fit .", "图形验证码不匹配", Level.ERROR, HttpStatus.BAD_REQUEST),

    /*滑动验证码开始 400200*/
    //验证码已失效,请重新获取
    DYNAMIC_CAPTCHA_INVALID(400100, "captcha invalid", "验证码已失效,请重新获取"),

    //验证码验证失败
    DYNAMIC_CAPTCHA_COORDINATE_ERROR(400101, "captcha coordinate error", "验证失败"),

    //验证码底图初始化失败,请检查路径
    CAPTCHA_CATEGORY_ERROR(400102, "captcha category error", "验证码分类错误"),

    CAPTCHA_HANDLE_NOT_EXIST(400107, "captcha handler not exist ", "验证码处理器不存在"),

    //滑动验证码接口验证失败数太多 get后验证失败次数过多
    DYNAMIC_CAPTCHA_REQ_LOCK_GET_ERROR(400103, "captcha get too many fail times,plz retry later ", "验证动态验证码错误次数过多,请稍后再试"),

    //check接口请求次数超限,请稍后再试!
    DYNAMIC_CAPTCHA_LIMIT_CHECK_ERROR(400104, "captcha validate too many fail times,plz retry later ", "接口验证失败次数过多,请稍后再试"),

    //verify接口请求次数超限,请稍后再试!
    DYNAMIC_CAPTCHA_REQ_LIMIT_VERIFY_ERROR(400105, "captcha validate too many fail times,plz retry later ", "验证码验证失败数过多,请稍后再试"),

    // 获取验证码失败 请联系管理员
    CAPTCHA_CANNOT_EMPTY(400106, "captcha cannot empty", "验证码不能为空"), CAPTCHA_PARAM_NOT_FIT(400107, "captcha  input param not fit", "验证码参数格式错误"), //验证码底图初始化失败,请检查路径
    DYNAMIC_CAPTCHA_BASEMAP_NULL(400108, "captcha background image init error", "底图未初始化成功,请检查路径"),

    /**
     * 需要授权
     */
    AUTH_401_NEED_AUTH(401001, "Need Authentication.", "登录过期,请重新登录", Level.INFO, HttpStatus.UNAUTHORIZED),

    /**
     * 没有权限
     */
    AUTH_403_FORBIDDEN(403000, "Permission deny.", "没有权限", Level.INFO, HttpStatus.FORBIDDEN),
    /**
     * 404
     */
    NOT_FOUND_404(404000, "NOT FOUND", "服务器未找到", Level.INFO, HttpStatus.NOT_FOUND),
    /**
     * 请求方法不支持
     */
    METHOD_NOT_ALLOWED(405000, "Method Not Allowed", "请求方法不支持", Level.INFO, HttpStatus.METHOD_NOT_ALLOWED),

    NOT_ACCEPTABLE(406000, "Not Acceptable", "请求ContentType不支持", Level.INFO, HttpStatus.NOT_ACCEPTABLE),

    /**
     * 请求超时
     */
    REQUEST_TIMEOUT(408000, "Request timeout.", "请求超时", Level.ERROR, HttpStatus.REQUEST_TIMEOUT),

    INVALID_REQUEST(412000, "Invalid Request", "无效请求", HttpStatus.PRECONDITION_FAILED),

    URI_TOO_LONG(414000, "URI Too Long.", "请求路径过长", Level.INFO, HttpStatus.URI_TOO_LONG),
    /**
     * contenttype 不支持
     */
    CONTENT_TYPE_INVALID(415000, "HttpMediaTypeNotSupported. ContentType(%s) is not acceptable.", "报文类型不支持", Level.INFO, HttpStatus.UNSUPPORTED_MEDIA_TYPE),


    /**
     * 不支持的请求体
     */
    REQUEST_BODY_INCORRECT(422000, "Entity format not supported。", "请求体类型不支持", Level.ERROR, HttpStatus.UNPROCESSABLE_ENTITY),


    /**
     * 限流
     */
    REQUEST_RAIT_LIMIT(429000, "Too Many Requests", "请稍后重试", Level.INFO, HttpStatus.TOO_MANY_REQUESTS),


    /**
     * 系统内部异常
     */
    SYSTEM_INTERNAL_ERROR(500000, "System internal error", "服务开小差啦", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    /**
     * 未知异常
     */
    UNKNOWN(500001, "Unknown error.", "未知错误", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    /**
     * 操作失败
     */
    OPT_FAILED(500002, "operation failed.", "操作失败", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    /**
     * NULL POINT
     */
    NULL_POINT_EXECETION(500003, "NULL_POINT_EXECETION", "空指针", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * IO_EXECETION
     */
    IO_EXECETION(500002, "IO_EXECETION", "io异常", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),


    /**
     * sql 愈发异常
     */
    BAD_SQL_GRAMMAR(500004, "BAD_SQL_GRAMMAR", "sql 语法异常"),

    DATA_INTEGRITY_VIOLATION(500005, "DATA_INTEGRITY_VIOLATION", "该数据正在被其它数据引用，请先删除引用关系，再进行数据删除操作");

    public int status;
    public String message;
    public String messageEn;
    public Level logLevel;
    public HttpStatus httpStatus;

    ServiceResponseCodeEnum(int status, String messageEn, Level logLevel, HttpStatus httpStatus) {
        this.status = status;
        this.messageEn = messageEn;
        this.logLevel = logLevel;
        this.httpStatus = httpStatus;
    }

    ServiceResponseCodeEnum(int status, String messageEn, String message, HttpStatus httpStatus) {
        this.status = status;
        this.message = message;
        this.messageEn = messageEn;
        this.httpStatus = httpStatus;
    }

    ServiceResponseCodeEnum(int status, String messageEn, String message, Level logLevel, HttpStatus httpStatus) {
        this.status = status;
        this.message = message;
        this.messageEn = messageEn;
        this.httpStatus = httpStatus;
        this.logLevel = logLevel;
    }

    ServiceResponseCodeEnum(int status, String messageEn, String message) {
        this.status = status;
        this.message = message;
        this.messageEn = messageEn;
    }

    ServiceResponseCodeEnum(int status, String messageEn, Level logLevel) {
        this.status = status;
        this.messageEn = messageEn;
        this.logLevel = logLevel;
    }

    ServiceResponseCodeEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    ServiceResponseCodeEnum(int status, String messageEn, HttpStatus httpStatus) {
        this.status = status;
        this.messageEn = messageEn;
        this.httpStatus = httpStatus;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageEn() {
        return messageEn;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public static ServiceResponseCodeEnum getByCode(int code) {
        Optional<ServiceResponseCodeEnum> first = asList(values()).stream().filter(d -> d.getStatus() == code).findFirst();
        return first.orElse(null);
    }
}
