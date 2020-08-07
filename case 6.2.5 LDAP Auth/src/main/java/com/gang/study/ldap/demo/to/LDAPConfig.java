package com.gang.study.ldap.demo.to;

/**
 * @Classname LDAPConfig
 * @Description TODO
 * @Date 2020/8/7 22:02
 * @Created by zengzg
 */
public class LDAPConfig {

    private String host;

    private String port = "389";

    private String account;

    private String password;

    /**
     * 是否开启SSL
     */
    private Boolean openSSL = Boolean.FALSE;

    /**
     * 根组织
     */
    private String baseContxt;

    /**
     * 认证方式
     */
    private LdapAuthType authType;

    /**
     * 超时时间
     */
    private Integer connectTimeout = 1000 * 60;
    private Integer readTimeout = 1000 * 60;

    public LDAPConfig(String host, String port, String account, String password, Boolean openSSL) {
        this.host = host;
        this.port = port;
        this.account = account;
        this.password = password;
        this.openSSL = openSSL;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getOpenSSL() {
        return openSSL;
    }

    public void setOpenSSL(Boolean openSSL) {
        this.openSSL = openSSL;
    }

    public LdapAuthType getAuthType() {
        return authType;
    }

    public void setAuthType(LdapAuthType authType) {
        this.authType = authType;
    }

    public String getBaseContxt() {
        return baseContxt;
    }

    public void setBaseContxt(String baseContxt) {
        this.baseContxt = baseContxt;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }
}
