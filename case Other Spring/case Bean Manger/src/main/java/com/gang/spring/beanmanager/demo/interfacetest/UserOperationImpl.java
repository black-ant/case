package com.gang.spring.beanmanager.demo.interfacetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Classname UserOperationImpl
 * @Description TODO
 * @Date 2020/12/21 11:01
 * @Created by zengzg
 */
@Service
public class UserOperationImpl implements IUserOperation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String doOperation() {
        logger.info("------> this is in doOperation <-------");
        return "success";
    }
}
