package com.gang.cloud.template.demo.entity;

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
@Table(name = "product")
public class ProductEntity {

    /**
     * 账户ID
     */
    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name")
    private BigDecimal productName;

    /**
     * 产品数量
     */
    @Column(name = "product_num")
    private BigDecimal productNum;


    /**
     * 产品单价
     */
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    public ProductEntity() {
    }

    public ProductEntity(Integer productId) {
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

    public BigDecimal getProductName() {
        return productName;
    }

    public void setProductName(BigDecimal productName) {
        this.productName = productName;
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
