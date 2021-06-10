package com.gang.cloud.template.demo.entity;

import java.math.BigDecimal;

/**
 * @Classname NoDataAccount
 * @Description 无数据库体验流程
 * @Created by zengzg
 */
public class NoDataProduct {

    /**
     * 账户ID
     */
    private String productID;

    /**
     * 产品数量
     */
    private BigDecimal productNum;


    /**
     * 产品单价
     */
    private BigDecimal unitPrice;

    public NoDataProduct() {
    }

    public NoDataProduct(String productID) {
        this.productID = productID;
        this.productNum = new BigDecimal(10);
        this.unitPrice = new BigDecimal(10);
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public BigDecimal getProductNum() {
        return productNum;
    }

    public void setProductNum(BigDecimal productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
