package com.gang.unittest.simple.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname UnitLogic
 * @Description TODO
 * @Date 2020/7/6 11:57
 * @Created by zengzg
 */
@Component
public class UnitLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String test() {
        logger.info("------> this is in test <-------");
        return "success";
    }

}
