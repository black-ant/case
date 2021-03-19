package com.gang.cloud.template.to;

import com.gang.cloud.template.type.ApiConstant;
import com.yiqiniu.easytrans.protocol.BusinessIdentifer;
import com.yiqiniu.easytrans.protocol.tcc.TccMethodRequest;

/**
 * @Classname CommonTO
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
@BusinessIdentifer(appId = ApiConstant.APPID, busCode = "pay", rpcTimeOut = 2000)
public class CommonRequestTO implements TccMethodRequest<CommonResponseTO> {

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
