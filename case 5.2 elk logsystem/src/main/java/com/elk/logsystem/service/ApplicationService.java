package com.elk.logsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;


/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/25 13:31
 * @Version 1.0
 **/
@Service
public class ApplicationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @InitBinder
    public void init() {
        logger.info("Application Service start 测试");
        for (int i = 0; i < 10; i++) {
            logger.error("something wrong. id={}; name=Ryan-{};", i, i);
        }
    }
}
