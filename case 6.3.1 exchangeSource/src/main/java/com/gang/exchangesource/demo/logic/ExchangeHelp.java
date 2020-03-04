package com.gang.exchangesource.demo.logic;

import com.gang.exchangesource.demo.to.ExchangeTO;
import com.gang.exchangesource.demo.to.MailSystemConfig;

/**
 * @Classname ExchangeHelp
 * @Description TODO
 * @Date 2020/3/3 18:42
 * @Created by zengzg
 */
public class ExchangeHelp {

    public static MailSystemConfig getConfig() {
        MailSystemConfig config = new MailSystemConfig("127.0.0.1", 8885, "wdhacpoc\\administrator", "zzg!@19950824");
        return config;
    }

    public static ExchangeTO getExchangeTO() {
        ExchangeTO to = new ExchangeTO();
        to.setAliasName("testAccount");
        to.setOuAddress("CN=testAccount,OU=ExchangeAccount,DC=wdhacpoc,DC=com,DC=cn");
        to.setDisplayName("testAccount");
        to.setEmailAddress("testAccount@wdhacpoc.com.cn");
        return to;
    }
}
