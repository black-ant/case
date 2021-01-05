package com.gang.study.annotation.demo.controller;

import com.gang.study.annotation.demo.model.NotNullModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("notnull")
public class NotNullController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("one")
    public void nullTest(@Valid NotNullModel notNullModel) {
        logger.info("----> is in notnull :{}", notNullModel);
    }

    @GetMapping("test")
    public void test() {
        logger.info("----> is in notnull :{}", "1111111");
    }
}
