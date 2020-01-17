package com.gang.study.springsource.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
public class TestLocalMessage {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;

    public void getLocal() {
        Locale locale = LocaleContextHolder.getLocale();
        logger.info("----->{}", messageSource.getMessage("com.gang.study.testzh", null, locale));
    }
}
