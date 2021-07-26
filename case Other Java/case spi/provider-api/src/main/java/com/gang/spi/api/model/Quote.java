package com.gang.spi.api.model;

import java.time.LocalDate;

/**
 * @Classname Quote
 * @Description TODO
 * @Date 2021/7/26
 * @Created by zengzg
 */
public class Quote {

    private String currency;

    private LocalDate date;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
