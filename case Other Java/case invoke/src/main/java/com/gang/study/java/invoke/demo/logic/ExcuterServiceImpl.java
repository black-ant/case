package com.gang.study.java.invoke.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname DoExcuterService
 * @Description TODO
 * @Date 2020/6/2 15:43
 * @Created by zengzg
 */
public class ExcuterServiceImpl implements IExcuterService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Integer doExcuter(int i) {
        logger.info("------> do doExcuter i :{} <-------", i);
        return i + 1;
    }
}
