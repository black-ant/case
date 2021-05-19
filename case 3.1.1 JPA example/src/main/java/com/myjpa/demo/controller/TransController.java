package com.myjpa.demo.controller;

import com.myjpa.demo.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TransController
 * @Description TODO
 * @Date 2021/5/17
 * @Created by zengzg
 */
@RequestMapping("trans")
@RestController
public class TransController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransactionService transactionService;

    @GetMapping("doTrans")
    public String doTrans() {
        return transactionService.doTrans();
    }

    @GetMapping("doExeTrans")
    public String doExeTrans() {
        logger.info("------> 执行 org.springframework.transaction.annotation.Transactional <-------");
        return transactionService.doExceptionTrans();
    }

    @GetMapping("doExeTransOther")
    public String doExeTransOther() {
        logger.info("------> 执行 javax.transaction.Transactional <-------");
        return transactionService.doExceptionTransOther();
    }

    @GetMapping("doExeTransNo")
    public String doExeTransNo() {
        logger.info("------> 无事务得异常处理 <-------");
        return transactionService.doExceptionTransNo();
    }
}
