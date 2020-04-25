package com.gang.study.sofaboot.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname APIServiceImpl
 * @Description TODO
 * @Date 2020/4/25 23:06
 * @Created by zengzg
 */
public class APIServiceImpl implements APIService {

    private String msg;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public APIServiceImpl(String msg) {
        this.msg = msg;
    }

    public String send() {
        logger.info("------> this is in APIServiceImpl {} <-------", msg);
        return null;
    }
}
