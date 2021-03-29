package com.gang.cloud.template.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.api.OrderClient;
import com.gang.cloud.template.demo.entity.AccountEntity;
import com.gang.cloud.template.demo.repository.AccountRepository;
import com.gang.cloud.template.demo.service.AccountServicce;
import com.gang.cloud.template.to.CommonAccountTO;
import com.gang.cloud.template.to.CommonOrderTO;
import com.gang.cloud.template.to.CommonProductTO;
import org.apache.dubbo.config.annotation.Reference;
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

    @Reference(lazy = true, check = false)
    private OrderClient orderClient;

    @Autowired
    private AccountServicce accountServicce;

    @GetMapping("list")
    public Collection<CommonAccountTO> list() {
        return accountServicce.list();
    }

    @GetMapping("get/{id}")
    public CommonAccountTO list(@PathVariable("id") String id) {
        return accountServicce.getOne(id);
    }

    @GetMapping("buy/{orderId}")
    public String buyProduct(@PathVariable("orderId") Integer orderId) {
        CommonOrderTO CommonOrderTO = orderClient.getOne(orderId);
        logger.info("------> AccountController : 订单查询成功 :{} <-------", JSONObject.toJSONString(CommonOrderTO));

        // 更新账户
//        AccountEntity account = accountRepository.getOne(CommonOrderTO.getAccountId());
//        account.setAccountAssets(account.getAccountAssets().subtract(CommonOrderTO.getTotalAmount()));
//        accountRepository.save(account);

        return "success";
    }

}
