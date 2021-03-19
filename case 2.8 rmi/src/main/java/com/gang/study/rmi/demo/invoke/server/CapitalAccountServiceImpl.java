package com.gang.study.rmi.demo.invoke.server;

import com.gang.study.rmi.demo.invoke.api.CapitalAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Classname CapitalAccountServiceImpl
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
@Component
public class CapitalAccountServiceImpl implements CapitalAccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BigDecimal getCapitalAccountByUserId(long userId) {

        logger.info("------> [CapitalAccountServiceImpl 调用成功] <-------");

        return BigDecimal.TEN;
    }
}
