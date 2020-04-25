package com.gang.study.sofaboot.bean.config;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname TestServiceImpl
 * @Description TODO
 * @Date 2020/4/25 22:35
 * @Created by zengzg
 */
@SofaService(uniqueId = "testServiceImpl")
public class TestServiceImpl implements TestService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String send() {
        logger.info("------> this is in send <-------");
        return "this is in send";
    }
}
