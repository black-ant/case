package com.gang.cloud.template.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Random;

/**
 * @Classname NoDataAccount
 * @Description 无数据库体验流程
 * @Created by zengzg
 */
public class CommonOrderTO implements Serializable {

    /**
     * 账户ID
     */
    private Integer orderId;

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


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
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
