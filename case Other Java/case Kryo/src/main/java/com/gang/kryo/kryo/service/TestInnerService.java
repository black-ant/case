package com.gang.kryo.kryo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname TestInnerService
 * @Description TODO
 * @Date 2021/3/24
 * @Created by zengzg
 */
@Component
public class TestInnerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @return
     */
    public String doTest() {
        logger.info("------> TestInnerService doTest <-------");
        return "Success";
    }

}
