package com.gang.spring.exceptionhandle.demo.config;

import com.gang.spring.exceptionhandle.demo.service.TestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@Component
public class TestAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value=TestException.class)
    @ResponseBody
    public void test() {
        logger.info("thsi is in handle ----333333333333");
    }

    @ExceptionHandler({Exception.class})
    public void tes1111t() {
        logger.info("thsi is in handle ----5555555555555");
    }
}
