package com.gang.cloud.resilience4j.service;

import com.gang.cloud.resilience4j.exceptions.BusinessException;
import com.gang.cloud.resilience4j.exceptions.DefaultTransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2021/6/10
 * @Created by zengzg
 */
@Component
public class TestService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int count = 0;

    public String doSomething() {
        logger.info("------> this is in doSomething <-------");
        return "success";
    }


    public String doSomething(String username) {
        logger.info("------> this is in doSomething :{} <-------", username);
        ++count;
        if (count == 4) {
            throw new DefaultTransactionException();
        } else if (count > 7) {
            throw new BusinessException();
        }
        logger.info("------> this is in doSomething count :{} <-------", count);
        return "success";
    }


    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        TestService.count = count;
    }
}
