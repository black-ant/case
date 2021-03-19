package com.gang.cloud.template.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.demo.client.OrderFeignClient;
import com.gang.cloud.template.demo.entity.NoDataAccount;
import com.gang.cloud.template.demo.repository.NoDataAccountRepository;
import com.gang.cloud.template.to.CommonOrderTO;
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
        CommonOrderTO CommonOrderTO = orderFeignClient.getById(orderId);
        logger.info("------> AccountController : 订单查询成功 :{} <-------", JSONObject.toJSONString(CommonOrderTO));

        // 更新账户
        NoDataAccount account = accountRepository.getAccountById(CommonOrderTO.getAccountId());
        account.setAccountAssets(account.getAccountAssets().subtract(CommonOrderTO.getTotalAmount()));
        accountRepository.updateAccount(account);

        return "success";
    }

}
