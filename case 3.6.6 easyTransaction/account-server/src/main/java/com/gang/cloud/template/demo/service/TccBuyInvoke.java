package com.gang.cloud.template.demo.service;

import com.gang.cloud.template.to.CommonOrderTO;
import com.gang.cloud.template.to.CommonRequestTO;
import com.gang.cloud.template.to.CommonResponseTO;
import com.yiqiniu.easytrans.protocol.BusinessProvider;
import com.yiqiniu.easytrans.protocol.tcc.TccMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Classname TccBuyInvoke
 * @Description 主要实现分布式事务的方法
 * @Date 2021/3/17
 * @Created by zengzg
 */
@Component
public class TccBuyInvoke implements TccMethod<CommonOrderTO, CommonResponseTO> {

    @Autowired
    private AccountBuyService buyService;

    /**
     * 分布式事务 ID
     */
    public static final String METHOD_NAME = "pay";

    @Override
    public CommonResponseTO doTry(CommonOrderTO orderTO) {
        buyService.buyProduct(orderTO);
        return new CommonResponseTO();
    }

    @Override
    public void doConfirm(CommonOrderTO param) {
        buyService.doConfirmPay(param);
    }

    @Override
    public void doCancel(CommonOrderTO param) {
        buyService.doCancelPay(param);
    }

    @Override
    public int getIdempotentType() {
        return BusinessProvider.IDENPOTENT_TYPE_FRAMEWORK;
    }

}
