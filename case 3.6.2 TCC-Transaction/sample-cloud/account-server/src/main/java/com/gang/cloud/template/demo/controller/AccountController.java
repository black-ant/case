package com.gang.cloud.template.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.demo.client.OrderFeignClient;
import com.gang.cloud.template.demo.entity.NoDataAccount;
import com.gang.cloud.template.demo.repository.NoDataAccountRepository;
import com.gang.cloud.template.demo.to.OrderTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @Classname AccountController
 * @Description TODO
 * @Created by zengzg
 */
@RequestMapping("/account")
@RestController
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NoDataAccountRepository accountRepository;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @GetMapping("list")
    public Collection<NoDataAccount> list() {
        return accountRepository.getDatabase().values();
    }

    @GetMapping("get/{id}")
    public NoDataAccount list(@PathVariable("id") String id) {
        return accountRepository.getAccountById(id);
    }

    @GetMapping("buy/{orderId}")
    public String buyProduct(@PathVariable("orderId") String orderId) {
        OrderTO orderTO = orderFeignClient.getById(orderId);
        logger.info("------> AccountController : 订单查询成功 :{} <-------", JSONObject.toJSONString(orderTO));

        // 更新账户
        NoDataAccount account = accountRepository.getAccountById(orderTO.getAccountId());
        account.setAccountAssets(account.getAccountAssets().subtract(orderTO.getTotalAmount()));
        accountRepository.updateAccount(account);

        return "success";
    }

}
