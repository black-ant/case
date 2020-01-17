package com.gang.spring.exceptionhandle.demo.controller;

import com.gang.spring.exceptionhandle.demo.service.TestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestTwo {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/case")
    public void conHandle() {
        logger.info("88888  ----  this is in chandle");
        throw new TestException();
    }
}
