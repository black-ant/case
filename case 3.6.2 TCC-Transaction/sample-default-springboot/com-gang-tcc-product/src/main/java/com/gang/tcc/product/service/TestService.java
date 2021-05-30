package com.gang.tcc.product.service;


import org.apache.dubbo.config.annotation.Service;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;

/**
 * @Classname TccService
 * @Description TODO
 * @Date 2021/5/28
 * @Created by zengzg
 */
@Service
public class TestService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Compensable
    public void remoteDoTry(TransactionContext transactionContext, String accountNo, BigDecimal amount) {

    }

    @Transactional
    @Compensable(confirmMethod = "remoteCommit", cancelMethod = "remoteRollback", asyncConfirm = false, asyncCancel = false)
    public void doTry(TransactionContext transactionContext, String accountNo, String to, BigDecimal amount) {
        //try阶段
        logger.info("------> this is in TestService doTry <-------");
    }

    @Transactional
    public void commit(TransactionContext transactionContext, String accountNo, String to, BigDecimal amount) {
        //commit阶段
        logger.info("------> this is in TestService commit <-------");
    }

    @Transactional
    public void rollback(TransactionContext transactionContext, String accountNo, String to, BigDecimal amount) {
        //rollback阶段
        logger.info("------> this is in TestService rollback <-------");
    }

}
