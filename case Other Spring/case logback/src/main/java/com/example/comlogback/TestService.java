package com.example.comlogback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2023/3/25
 * @Created by zengzg
 */
@Service
public class TestService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostConstruct
    public void test() {
        logger.info("------> this is ok <-------");
    }
}
