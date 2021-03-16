package com.gang.cloud.template.demo.to;

import java.math.BigDecimal;

/**
 * @Classname ProductTO
 * @Description
 * @Created by zengzg
 */
public class ProductTO {

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
