package com.gang.cloud.template.to;

import java.math.BigDecimal;

/**
 * @Classname NoDataAccount
 * @Description 无数据库体验流程
 * @Created by zengzg
 */
public class CommonAccountTO {

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 账户积分
     */
    private BigDecimal accountIntegral;

    /**
     * 账户金额
     */
    private BigDecimal accountAssets;

    public CommonAccountTO() {
    }

    public CommonAccountTO(String id) {
        this.accountId = id;
        this.accountIntegral = new BigDecimal(10);
        this.accountAssets = new BigDecimal(1000);
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAccountIntegral() {
        return accountIntegral;
    }

    public void setAccountIntegral(BigDecimal accountIntegral) {
        this.accountIntegral = accountIntegral;
    }

    public BigDecimal getAccountAssets() {
        return accountAssets;
    }

    public void setAccountAssets(BigDecimal accountAssets) {
        this.accountAssets = accountAssets;
    }
}
