package com.gang.study.ldap.demo.to;

/**
 * @Classname LdapAuthType
 * @Description TODO
 * @Date 2020/8/7 22:09
 * @Created by zengzg
 */
public enum LdapAuthType {

    /**
     * 无需账号密码认证
     */
    NONE("none"),

    /**
     * 账号密码认证
     */
    ACCOUNT("simple"),

    /**
     * 强认证? 未确定 , 未使用
     */
    STRONG("strong");

    private String code;

    LdapAuthType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

