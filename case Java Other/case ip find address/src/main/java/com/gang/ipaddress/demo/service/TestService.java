package com.gang.ipaddress.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2020/11/9 22:29
 * @Created by zengzg
 */
@Service
public class TestService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String address = IpRegionUtil.getIpCity("183.95.67.94");
        logger.info("------> this is address :{} <-------", address);
    }
}
