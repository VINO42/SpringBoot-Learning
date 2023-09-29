package io.github.vino42.event;

import org.springframework.context.ApplicationEvent;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/13 19:25
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class SyncEvent extends ApplicationEvent {

    private String msg;

    public SyncEvent(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
