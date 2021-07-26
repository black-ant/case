package com.gang.spi.demo.service;

import com.gang.spi.api.service.ExchangeRateProvider;
import com.gang.spi.api.service.ProviderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/7/26
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> [App 中获取]  <-------");

        ProviderManager providerManager = new ProviderManager();

        Iterator<ExchangeRateProvider> providers = providerManager.providers(true);


        while (providers.hasNext()) {
            logger.info("------> [providers 获取完成 :{}] <-------", providers.next().create());
        }

    }
}
