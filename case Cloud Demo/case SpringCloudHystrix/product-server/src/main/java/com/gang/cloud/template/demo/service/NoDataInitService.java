package com.gang.cloud.template.demo.service;

import com.gang.cloud.template.demo.entity.NoDataProduct;
import com.gang.cloud.template.demo.repository.NoDataProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname NoDataInitService
 * @Description TODO
 * @Created by zengzg
 */
@Component
public class NoDataInitService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NoDataProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> [初始化账户数据 1 ] <-------");
        NoDataProduct account = new NoDataProduct("1");
        productRepository.addNoDataProduct(account);
    }
}
