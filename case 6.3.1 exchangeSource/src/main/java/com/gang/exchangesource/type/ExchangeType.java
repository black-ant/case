package com.gang.exchangesource.type;

/**
 * @Classname ExchangeType
 * @Description TODO
 * @Date 2020/3/3 15:24
 * @Created by zengzg
 */
public enum ExchangeType {

    TYPE_EMAIL_ACCOUNT("ACCOUNT"),
    TYPE_EMAIL_GROUP("ACCOUNT"),

    //Operation Type
    OP_NEW_USER("New-MailUser"),
    OP_NEW_GROUP("New-DistributionGroup"),
    OP_ENABL_EMAIL("Enable-Mailbox"),
    OP_DISABL_EMAIL("Disable-Mailbox"),
    OP_GETMAILBOX("Get-Mailbox"),
    OP_ENABLE_COMGROUP("Enable-DistributionGroup"),
    OP_DISABLE_COMGROUP("Disable-DistributionGroup"),
    OP_SET_COMGROUP("set-DistributionGroup"),
    OP_GET_COMGROUP("Get-DistributionGroup");


    ExchangeType(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isType(ExchangeType type) {
        if (type == null) {
            return Boolean.FALSE;
        }
        return this.code.equals(type.getCode());
    }
}
