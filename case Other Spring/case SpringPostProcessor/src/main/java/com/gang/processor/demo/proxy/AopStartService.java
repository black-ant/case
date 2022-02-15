package com.gang.processor.demo.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname AopStartService
 * @Description TODO
 * @Date 2021/10/5
 * @Created by zengzg
 */
@Service
public class AopStartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Source source;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> 创建 AopStartService 代理 <-------");
//        source.method();
    }
}
