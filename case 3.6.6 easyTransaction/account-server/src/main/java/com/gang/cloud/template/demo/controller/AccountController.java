package com.gang.cloud.template.demo.controller;

import com.gang.cloud.template.demo.entity.NoDataAccount;
import com.gang.cloud.template.demo.repository.NoDataAccountRepository;
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
public class AccountController implements com.gang.cloud.template.demo.api.IAccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NoDataAccountRepository accountRepository;


    @Override
    @GetMapping("list")
    public Collection<NoDataAccount> list() {
        return accountRepository.getDatabase().values();
    }

    @Override
    @GetMapping("get/{id}")
    public NoDataAccount list(@PathVariable("id") String id) {
        return accountRepository.getAccountById(id);
    }

    @Override
    @GetMapping("buy/{orderId}")
    public String buyProduct(@PathVariable("orderId") String orderId) {
        return "success";
    }

}
