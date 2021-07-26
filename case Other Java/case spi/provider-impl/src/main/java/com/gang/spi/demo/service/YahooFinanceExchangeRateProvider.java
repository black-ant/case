package com.gang.spi.demo.service;

import com.gang.spi.api.service.ExchangeRateProvider;
import com.gang.spi.api.service.QuoteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname YahooFinanceExchangeRateProvider
 * @Description TODO
 * @Date 2021/7/26
 * @Created by zengzg
 */
public class YahooFinanceExchangeRateProvider implements ExchangeRateProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public QuoteManager create() {
        logger.info("------> this is create <-------");
        return new YahooQuoteManagerImpl();
    }
}
