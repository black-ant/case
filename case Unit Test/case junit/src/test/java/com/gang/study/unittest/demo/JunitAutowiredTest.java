package com.gang.study.unittest.demo;

import com.gang.study.unittest.demo.logic.TestAutowiredLogic;
import com.gang.study.unittest.demo.logic.TestLogic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @Classname JunitAutowiredTest
 * @Description TODO
 * @Date 2020/7/6 18:00
 * @Created by zengzg
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class JunitAutowiredTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @InjectMocks
    private TestAutowiredLogic giteeConfig = new TestAutowiredLogic();

    @Mock
    private TestLogic testLogic = new TestLogic();

    @Before
    public void initBefore() {
        ReflectionTestUtils.setField(giteeConfig, "testLogic", testLogic);
        String word = "mocked Return";
        Mockito.when(testLogic.returnTest(Mockito.anyString())).thenReturn(word);
    }

    @Test
    public void doTest() {
        logger.info("------> this is in test <-------");
        giteeConfig.test();
    }
}
