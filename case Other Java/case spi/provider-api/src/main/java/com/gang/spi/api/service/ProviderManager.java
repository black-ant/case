package com.gang.spi.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/7/26
 * @Created by zengzg
 */
public class ProviderManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Boolean refresh = Boolean.TRUE;

    public Iterator<ExchangeRateProvider> providers(boolean refresh) {
        logger.info("------> [Step 1 : 进入 Provider 处理流程] <-------");
        ServiceLoader<ExchangeRateProvider> loader = ServiceLoader.load(ExchangeRateProvider.class);

        if (refresh) {
            loader.reload();
        }
        Iterator<ExchangeRateProvider> provider = loader.iterator();


        return provider;

    }


}
