package com.gang.tcc.order.service;

import com.gang.tcc.product.service.TestService;
import org.apache.dubbo.config.annotation.DubboReference;
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
public class TccService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @DubboReference(check = false, retries = 0, timeout = 30000) //dubbo rpc远程过程调用
    private TestService testService;

    @Transactional
    @Compensable(confirmMethod = "commit", cancelMethod = "rollback", asyncConfirm = false, asyncCancel = false)
    public void doTry(TransactionContext transactionContext, String accountNo, String to, BigDecimal amount) {

        logger.info("------> this is in TccService doTry <-------");
        //try阶段
        testService.remoteDoTry(transactionContext, accountNo, amount);
    }

    @Transactional
    public void commit(TransactionContext transactionContext, String accountNo, String to, BigDecimal amount) {
        //commit阶段

        logger.info("------> this is in TccService commit <-------");
    }

    @Transactional
    public void rollback(TransactionContext transactionContext, String accountNo, String to, BigDecimal amount) {
        //rollback阶段
        logger.info("------> this is in TccService rollback <-------");
    }

}
