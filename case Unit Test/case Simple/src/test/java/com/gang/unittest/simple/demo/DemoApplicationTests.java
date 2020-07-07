package com.gang.unittest.simple.demo;

import com.gang.unittest.simple.demo.logic.UnitLogic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spring 的单元测试方式是最容易的
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UnitLogic logic;

    @Test
    public void contextLoads() {
        logger.info("------> this is in loads <-------");
        logic.test();
    }

}
