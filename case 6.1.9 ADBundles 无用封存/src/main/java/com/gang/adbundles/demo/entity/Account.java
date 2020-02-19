package com.gang.adbundles.demo.entity;

import com.gang.adbundles.demo.annotation.ADAccount;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/25 18:43
 * @Version 1.0
 **/

public class Account {

    @ADAccount(adName = "cn")
    private String cn;
    @ADAccount(adName = "displayName")
    private String displayName;
    @ADAccount(adName = "__NAME__")
    private String name;
    @ADAccount(adName = "USER_CODE")
    private String userCode;
}
