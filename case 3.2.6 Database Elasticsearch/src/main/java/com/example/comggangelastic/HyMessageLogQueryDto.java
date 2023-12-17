package com.example.comggangelastic;

import lombok.Data;

import java.util.Date;

/**
 * @Classname HyMessageSendDto
 * @Description 消息发送主体
 * @Date 2022/10/25
 */
@Data
public class HyMessageLogQueryDto {

    /**
     * 消息发送唯一Id (不传则唯一生成)
     */
    private String requestId;

    /**
     * 当前发送批量事件ID
     */
    private String batchNo;

    /**
     * 相对而言批量接收人的情况很少 ,采用单个用户发送
     */
    private String msgTarget;

    /**
     * 推送渠道
     */
    private String msgChannel;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 推送状态码
     */
    private String pushStatusCode;


}
