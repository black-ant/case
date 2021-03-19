package com.gang.cloud.template.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.demo.entity.NoDataAccount;
import com.gang.cloud.template.demo.repository.NoDataAccountRepository;
import com.gang.cloud.template.to.CommonOrderTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Classname AccountBuyService
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
@Component
public class AccountBuyService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NoDataAccountRepository accountRepository;

    public String buyProduct(CommonOrderTO orderTO) {
        logger.info("------> AccountController : 订单查询成功 :{} <-------", JSONObject.toJSONString(orderTO));

        // 更新账户
        NoDataAccount account = accountRepository.getAccountById(orderTO.getAccountId());
        account.setAccountAssets(account.getAccountAssets().subtract(orderTO.getTotalAmount()));
        accountRepository.updateAccount(account);

        return "success";
    }

    @Transactional
    public void doConfirmPay(CommonOrderTO param) {
        logger.info("------> 确定 Pay 业务 <-------");
    }

    @Transactional
    public void doCancelPay(CommonOrderTO param) {
        logger.info("------> 取消 Pay 业务 <-------");
    }


}
