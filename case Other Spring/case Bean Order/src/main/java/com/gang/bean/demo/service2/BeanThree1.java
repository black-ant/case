package com.gang.bean.demo.service2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname BeanThree1
 * @Description TODO
 * @Date 2022/10/22
 * @Created by zengzg
 */
//@Service
public class BeanThree1  implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public BeanThree1() {
        logger.info("------> 创建 BeanThree1 <-------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> BeanThree1 Run <-------");
    }
}
