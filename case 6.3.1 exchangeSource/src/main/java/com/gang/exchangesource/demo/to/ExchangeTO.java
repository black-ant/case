package com.gang.exchangesource.demo.to;

import com.gang.exchangesource.type.ADAnnotation;

/**
 * @Classname ExchangeTO
 * @Description TODO
 * @Date 2020/3/3 18:14
 * @Created by zengzg
 */
public class ExchangeTO extends SyncBaseBean {

    @ADAnnotation(alias = "Identity", description = "操作ID")
    private String ouAddress;

    @ADAnnotation(alias = "Alias", description = "别名")
    private String aliasName;

    @ADAnnotation(alias = "DisplayName", description = "显示名称")
    private String displayName;

    @ADAnnotation(alias = "PrimarySMTPAddress", description = "邮箱")
    private String emailAddress;

    public String getOuAddress() {
        return ouAddress;
    }

    public void setOuAddress(String ouAddress) {
        this.ouAddress = ouAddress;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
