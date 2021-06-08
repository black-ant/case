package com.gang.sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/6/7
 * @Created by zengzg
 */
@Component
public class StartService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void selectForUpdate() {
        logger.info("------> selectForUpdate <-------");
    }

    public void releaseLock() {
        logger.info("------> releaseLock <-------");
    }
}
