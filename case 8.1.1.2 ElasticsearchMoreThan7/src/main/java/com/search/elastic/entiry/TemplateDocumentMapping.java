package com.search.elastic.entiry;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Classname TemplateDocumentMapping
 * @Description TODO
 * @Date 2021/9/9
 * @Created by zengzg
 */
@Document(indexName = "login_", shards = 5, replicas = 1)
public class TemplateDocumentMapping {

    // @Id标记数据主键
    @Id
    private String id;

    // @Field标记字段。name:映射es中的字段;type:字段类型
    @Field(name = "username_", type = FieldType.Keyword)
    private String username;

    @Field(name = "content_", type = FieldType.Text)
    private String content;

    @Field(name = "data_", type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date loginTime;

    // 如果java类型使用java.util.Date,反序列化会失败
    @Field(name = "publish_date_", type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime publishDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }
}
