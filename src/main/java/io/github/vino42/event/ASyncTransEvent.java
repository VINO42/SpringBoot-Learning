package io.github.vino42.event;

import org.springframework.context.ApplicationEvent;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/13 19:25
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class ASyncTransEvent extends ApplicationEvent {

    private String msg;

    public ASyncTransEvent(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
