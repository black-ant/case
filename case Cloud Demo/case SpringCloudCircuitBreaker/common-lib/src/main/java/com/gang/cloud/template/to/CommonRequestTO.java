package com.gang.cloud.template.to;

/**
 * @Classname CommonTO
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
public class CommonRequestTO {

    private String orderId;

    private String accountId;

    private String productId;

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
}
