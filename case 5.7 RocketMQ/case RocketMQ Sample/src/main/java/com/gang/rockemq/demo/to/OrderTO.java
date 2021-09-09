package com.gang.rockemq.demo.to;

/**
 * @Classname OrderTO
 * @Description TODO
 * @Date 2021/9/8
 * @Created by zengzg
 */
public class OrderTO {

    private String orderId;

    private Integer productNum;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }
}
