package io.github.vino42.util;

import lombok.Data;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/2 1:48
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 后端websocket上下线消息通知实体， 这个应该单独抽出为公共
 * =====================================================================================
 */
@Data
public class KafkaServiceMessage {
    /**
     * ip地址
     */
    private String serviceIp;
    /**
     * 服务状态
     */
    private Integer serviceStatus;
    /**
     * 时间戳
     */
    private Long timestamp;
}
