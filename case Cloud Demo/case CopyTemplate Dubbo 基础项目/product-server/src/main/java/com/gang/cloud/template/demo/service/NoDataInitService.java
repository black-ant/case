package com.gang.cloud.template.demo.service;

import com.gang.cloud.template.demo.entity.ProductEntity;
import com.gang.cloud.template.demo.repository.ProductRepository;
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
    private ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> [初始化账户数据 1 ] <-------");
        ProductEntity account = new ProductEntity(1);
        productRepository.save(account);
    }
}
