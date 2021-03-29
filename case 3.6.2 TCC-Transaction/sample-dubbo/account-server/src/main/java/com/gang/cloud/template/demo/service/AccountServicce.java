package com.gang.cloud.template.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.api.AccountClient;
import com.gang.cloud.template.api.OrderClient;
import com.gang.cloud.template.demo.entity.AccountEntity;
import com.gang.cloud.template.demo.repository.AccountRepository;
import com.gang.cloud.template.exception.OrderException;
import com.gang.cloud.template.to.CommonAccountTO;
import com.gang.cloud.template.to.CommonOrderTO;
import com.gang.cloud.template.transaction.DefaultDubboTransactionContextEditor;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.mengyun.tcctransaction.api.Compensable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname AccountServicce
 * @Description TODO
 * @Date 2021/3/21
 * @Created by zengzg
 */
@Component
@Service
public class AccountServicce implements AccountClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @InitBinder
    public void init() {
        logger.info("------> ProductService init() <-------");
    }

    @Autowired
    private AccountRepository accountRepository;

    @Reference(lazy = true, check = false)
    OrderClient orderClient;

    /**
     * 返回 List 集合
     *
     * @return
     */
    public List<CommonAccountTO> list() {
        List<AccountEntity> productSearch = accountRepository.findAll();
        List<CommonAccountTO> response = new ArrayList<>();
        productSearch.stream().map(item -> {
            CommonAccountTO productTO = new CommonAccountTO();
            BeanUtils.copyProperties(item, productTO);
            return productTO;
        }).forEach(item -> response.add(item));
        return response;
    }

    /**
     * 返回单个集合c
     *
     * @param id
     * @return
     */
    public CommonAccountTO getOne(String id) {
        AccountEntity productTO = accountRepository.findById(id).orElse(new AccountEntity());
        CommonAccountTO responseTO = new CommonAccountTO();
        BeanUtils.copyProperties(productTO, responseTO);
        return responseTO;
    }

    @Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", transactionContextEditor = DefaultDubboTransactionContextEditor.class)
    public String buyProduct(Integer orderId) {
        CommonOrderTO orderTO = orderClient.getOne(orderId);
        logger.info("------> AccountController : 订单查询成功 :{} <-------", JSONObject.toJSONString(orderTO));

        // 更新账户
        AccountEntity account = accountRepository.findById(orderTO.getAccountId()).orElse(new AccountEntity());

        if (account.getAccountAssets().compareTo(orderTO.getTotalAmount()) > 0) {
            account.setAccountAssets(account.getAccountAssets().subtract(orderTO.getTotalAmount()));
        } else {
            throw new OrderException();
        }

        accountRepository.save(account);
        return "[产品消费完成 , 剩余 [" + account.getAccountAssets() + "] ";
    }

    @Transactional
    public void confirmRecord(Integer tradeOrderDto) {
        logger.info("------> AccountController confirmRecord :{} <-------", tradeOrderDto);
    }

    @Transactional
    public void cancelRecord(Integer tradeOrderDto) {
        logger.info("------> AccountController cancelRecord :{} <-------", tradeOrderDto);
    }
}
