package com.gang.spi.api.service;

import com.gang.spi.api.model.Quote;

import java.time.LocalDate;
import java.util.List;

/**
 * @Classname QuoteManager
 * @Description TODO
 * @Date 2021/7/26
 * @Created by zengzg
 */
public interface QuoteManager {

    List<Quote> getQuotes(String baseCurrency, LocalDate date);

}
