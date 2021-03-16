package com.gang.cloud.template.demo.entity;

import cn.hutool.core.lang.UUID;

import java.math.BigDecimal;

/**
 * @Classname NoDataAccount
 * @Description 无数据库体验流程
 * @Created by zengzg
 */
public class NoDataOrder {

    /**
     * 账户ID
     */
    private String orderId;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 产品ID
     */
    private String productId;

    /**
     * 产品数量
     */
    private BigDecimal productNum;


    /**
     * 总金额
     */
    private BigDecimal totalAmount;


    public NoDataOrder() {
        this.orderId = UUID.fastUUID().toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getProductNum() {
        return productNum;
    }

    public void setProductNum(BigDecimal productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
