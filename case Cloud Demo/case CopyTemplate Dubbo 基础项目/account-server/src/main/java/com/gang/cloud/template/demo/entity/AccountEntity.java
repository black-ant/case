package com.gang.cloud.template.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Classname AccountEntity
 * @Description 无数据库体验流程
 * @Created by zengzg
 */
@Entity
@Table(name = "account")
public class AccountEntity {

    /**
     * 账户ID
     */
    @Id
    private String accountId;

    @Column(name = "account_name")
    private String accountName;

    /**
     * 账户积分
     */
    @Column(name = "account_integral")
    private BigDecimal accountIntegral;

    /**
     * 账户金额
     */
    @Column(name = "account_assets")
    private BigDecimal accountAssets;

    public AccountEntity() {
    }

    public AccountEntity(String id) {
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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
