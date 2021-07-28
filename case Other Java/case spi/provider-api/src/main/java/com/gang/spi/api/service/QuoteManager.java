package com.gang.spi.api.service;

import java.time.LocalDate;
import java.util.List;

/**
 * @Classname QuoteManager
 * @Description TODO
 * @Date 2021/7/26
 * @Created by zengzg
 */
public interface QuoteManager {

    List<String> getQuotes(String baseCurrency, LocalDate date);

}
