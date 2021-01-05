package com.gang.exchangesource.demo.to;

import com.gang.exchangesource.type.ADAnnotation;

/**
 * @Classname ExchangeConfig
 * @Description TODO
 * @Date 2020/3/3 18:40
 * @Created by zengzg
 */
public class MailSystemConfig extends SyncBaseBean {

    private Boolean autoCreate;

    @ADAnnotation(alias = "ip", description = "服务器IP")
    private String host;
    private Integer port;

    @ADAnnotation(alias = "username", description = "操作ID")
    private String username;

    @ADAnnotation(alias = "password", description = "操作ID")
    private String password;

    private Integer timeOut;

    public MailSystemConfig(String host, Integer port, String account, String password) {
        this.host = host;
        this.port = port;
        this.username = account;
        this.password = password;
        this.timeOut = 500000;
    }

    public MailSystemConfig openAutoCreateEmail() {
        this.setAutoCreate(Boolean.TRUE);
        return this;
    }


    public Boolean getAutoCreate() {
        return autoCreate;
    }

    public void setAutoCreate(Boolean autoCreate) {
        this.autoCreate = autoCreate;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }
}
