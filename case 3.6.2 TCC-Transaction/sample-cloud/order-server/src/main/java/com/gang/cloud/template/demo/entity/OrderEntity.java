package com.gang.cloud.template.demo.entity;

import cn.hutool.core.lang.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Classname NoDataAccount
 * @Description 无数据库体验流程
 * @Created by zengzg
 */
@Entity
@Table(name ="order_table")
public class OrderEntity {

    /**
     * 账户ID
     */
    @Id
    private String orderId;

    /**
     * 账户ID
     */
    @Column(name = "account_id")
    private String accountId;

    /**
     * 产品ID
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 产品数量
     */
    @Column(name = "product_num")
    private BigDecimal productNum;


    /**
     * 总金额
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;


    public OrderEntity() {
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
