package com.gang.unittest.simple.demo;

import com.gang.unittest.simple.demo.logic.IUserService;
import com.gang.unittest.simple.demo.logic.UnitLogic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Spring 的单元测试方式是最容易的
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UnitLogic logic;

    @Autowired
    private IUserService userService;

    @Test
    public void contextLoads() {
        //        logger.info("------> this is in loads <-------");
        //        logic.test();

//        userService.getUser();
        List<Long> activityIds = new ArrayList<>();
        activityIds.add(null);
        if (CollectionUtils.isEmpty(activityIds)) {
            logger.info("------>  <-------");
        }else{
            logger.info("------>  <-------");
        }
    }


}
