package com.gang.spi.demo.service;

import com.gang.spi.api.model.Quote;
import com.gang.spi.api.service.QuoteManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname YahooQuoteManagerImpl
 * @Description TODO
 * @Date 2021/7/26
 * @Created by zengzg
 */
public class YahooQuoteManagerImpl implements QuoteManager {

    @Override
    public List<Quote> getQuotes(String baseCurrency, LocalDate date) {
        // fetch from Yahoo API

        return new ArrayList<>();
    }
}
