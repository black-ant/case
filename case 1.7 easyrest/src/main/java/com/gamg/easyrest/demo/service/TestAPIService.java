package com.gamg.easyrest.demo.service;

import com.gamg.easyrest.demo.controller.TestAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Test API 的 实现类
 * 注意 ： 此处要加 @Component 用于 spring 的托管
 */
@Component
public class TestAPIService implements TestAPI {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void showMsg() {
        logger.info("show msg is start");
    }
}
