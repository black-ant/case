package com.gang.cloud.dubbo.provide.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @Classname HelloService
 * @Description TODO
 * @Date 2020/8/10 22:50
 * @Created by zengzg
 */
@Service(interfaceClass = IHelloService.class)
@Component
public class HelloService implements IHelloService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getUserAddressList(String userId) {
        logger.info("------> this <-------");
        return "success";
    }
}
