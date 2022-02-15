package com.gang.xxljob.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Test
    public void contextLoads() {
        logger.info("------> " + Boolean.valueOf("") + " <-------");
    }

}
