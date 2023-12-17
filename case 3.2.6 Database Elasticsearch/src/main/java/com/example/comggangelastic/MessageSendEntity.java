package com.example.comggangelastic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @Classname MessageSendEntity
 * @Description 消息发送体
 * @Date 2022/10/25
 */
@Data
@Document(indexName = "biz-member-message-sendlog")
public class MessageSendEntity {

    /**
     * 内部消息ID
     */
    @Id
    private Long id;

    /**
     * 外部请求ID,理论上唯一
     */
    @Field(type = FieldType.Keyword)
    private String requestId;

    /**
     * 消息发送渠道 @ MessageSendChannelEnum
     */
    @Field(type = FieldType.Keyword)
    private String msgChannel;

    /**
     * 消息发送应用ID
     */
    @Field(type = FieldType.Keyword)
    private String appId;

    /**
     * 消息发送标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String msgTitle;

    /**
     * 消息发送内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String msgContent;

    /**
     * 消息发送人
     */
    @Field(type = FieldType.Keyword)
    private String msgTarget;

    /**
     * 批次编号:营销编号,券编号等
     */
    @Field(type = FieldType.Keyword)
    private String batchNo;

    /**
     * 消息消费时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 消息推送完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date, format = DateFormat.basic_date, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 消息推送状态码
     */
    @Field(type = FieldType.Keyword)
    private String pushStatusCode;

    /**
     * 消息推送返回体
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String pushResponseBody;


}
