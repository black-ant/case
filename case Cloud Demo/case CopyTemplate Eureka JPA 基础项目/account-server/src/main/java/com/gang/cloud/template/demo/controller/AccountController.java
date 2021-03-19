package com.gang.cloud.template.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.demo.client.OrderFeignClient;
import com.gang.cloud.template.demo.entity.AccountEntity;
import com.gang.cloud.template.demo.repository.AccountRepository;
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
    private AccountRepository accountRepository;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @GetMapping("list")
    public Collection<AccountEntity> list() {
        return accountRepository.findAll();
    }

    @GetMapping("get/{id}")
    public AccountEntity list(@PathVariable("id") String id) {
        return accountRepository.getOne(id);
    }

    @GetMapping("buy/{orderId}")
    public String buyProduct(@PathVariable("orderId") String orderId) {
        CommonOrderTO CommonOrderTO = orderFeignClient.getById(orderId);
        logger.info("------> AccountController : 订单查询成功 :{} <-------", JSONObject.toJSONString(CommonOrderTO));

        // 更新账户
        AccountEntity account = accountRepository.getOne(CommonOrderTO.getAccountId());
        account.setAccountAssets(account.getAccountAssets().subtract(CommonOrderTO.getTotalAmount()));
        accountRepository.save(account);

        return "success";
    }

}
