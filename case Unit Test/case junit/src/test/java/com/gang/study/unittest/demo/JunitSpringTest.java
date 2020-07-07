package com.gang.study.unittest.demo;

import com.gang.study.unittest.demo.logic.TestLogic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Classname JunitSpringTest
 * @Description TODO
 * @Date 2020/7/6 15:19
 * @Created by zengzg
 */
@RunWith(SpringRunner.class)
public class JunitSpringTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @InjectMocks
    private TestLogic logic = new TestLogic();

    @Before
    public void doBefore() {
        logger.info("------> this is in before <-------");
    }

    @Test
    public void doTest() {
        logic.doTest();
    }
}
