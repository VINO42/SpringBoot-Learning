package io.github.vino42.hashring;

import lombok.Data;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/2 1:48
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : kafka消息json实体 服务上下线消息
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
