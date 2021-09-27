package com.gang.seata.sharding.demo.pojo;

import java.math.BigDecimal;

public class Goods {

    //商品id
    Long goodsId;

    //商品名称
    private String goodsName;

    //商品标题
    private String subject;

    //商品价格
    private BigDecimal price;

    //库存
    int stock;

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String toString() {
        return " Goods:goodsId=" + goodsId + " goodsName=" + goodsName + " subject=" + subject + " price=" + price + " stock=" + stock;
    }
}
