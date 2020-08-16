package com.gang.cloud.consumer.demo.service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname OrderServiceImpl
 * @Description TODO
 * @Date 2020/8/16 11:39
 * @Created by zengzg
 */
@Service
public class OrderServiceImpl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference
    IUserService userService;

    public String initOrder(String userId) {
        logger.info("------> this is in order <-------");
        String info = userService.getUserAddressList("");

        logger.info("------> this is in info :{} <-------", info);
        return "success";
    }
}
