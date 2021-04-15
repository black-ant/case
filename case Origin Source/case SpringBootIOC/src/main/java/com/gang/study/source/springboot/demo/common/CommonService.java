package com.gang.study.source.springboot.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Classname CommonService
 * @Description TODO
 * @Date 2021/3/2 15:24
 * @Created by zengzg
 */
@Service
public class CommonService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Bean
//    public Other2Service getOtherService() {
//        return new Other2Service();
//    }

    /**
     * 测试方法
     *
     * @return
     */
    public String showInfo() {
        logger.info("------> this is showInfo <-------");
        return "success";
    }

}
