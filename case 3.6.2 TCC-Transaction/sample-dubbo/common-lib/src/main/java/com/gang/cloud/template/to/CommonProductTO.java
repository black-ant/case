package com.gang.cloud.template.to;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname NoDataAccount
 * @Description 无数据库体验流程
 * @Created by zengzg
 */
public class CommonProductTO implements Serializable {

    /**
     * 账户ID
     */
    private Integer productId;

    /**
     * 产品数量
     */
    private BigDecimal productNum;


    /**
     * 产品单价
     */
    private BigDecimal unitPrice;

    public CommonProductTO() {
    }

    public CommonProductTO(Integer productId) {
        this.productId = productId;
        this.productNum = new BigDecimal(10);
        this.unitPrice = new BigDecimal(10);
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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
