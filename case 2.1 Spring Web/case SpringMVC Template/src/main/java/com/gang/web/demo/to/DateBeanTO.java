package com.gang.web.demo.to;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * @Classname DateBeanTO
 * @Description TODO
 * @Date 2021/11/2
 * @Created by zengzg
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateBeanTO {

    private String username;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createDate;

    @JsonIgnore
    private String ignoreField;

    @JSONField(name = "age")
    private String testAge;

    private String nullValue;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getIgnoreField() {
        return ignoreField;
    }

    public void setIgnoreField(String ignoreField) {
        this.ignoreField = ignoreField;
    }

    public String getTestAge() {
        return testAge;
    }

    public void setTestAge(String testAge) {
        this.testAge = testAge;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }
}
