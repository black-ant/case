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
 * @Classname JunitVerifyTest
 * @Description TODO
 * @Date 2020/7/6 22:18
 * @Created by zengzg
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class JunitVerifyTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @InjectMocks
    private TestAutowiredLogic testAutowiredLogic = new TestAutowiredLogic();

    @Mock
    private TestLogic testLogic = new TestLogic();

    @Before
    public void initBefore() {
        ReflectionTestUtils.setField(testAutowiredLogic, "testLogic", testLogic);
        //        String word = "mocked Return";
        //        Mockito.when(testLogic.returnTest(Mockito.anyString())).thenReturn(word);
        Mockito.when(testLogic.returnTest("some arg")).thenReturn("one", "two", "three");
    }

    @Test
    public void doTest() {
        logger.info("------> this is in test <-------");
        testAutowiredLogic.test();

        //        Mockito.verify(testLogic).doTest();
        //        Mockito.verify(testLogic, Mockito.times(1)).doTest();
        //        Mockito.verify(testLogic, Mockito.times(0)).doTest();
        //        Mockito.verify(testLogic, Mockito.atLeast(1)).doTest();
        //        Mockito.verify(testLogic, Mockito.atMost(10)).doTest();

//        Mockito.verifyZeroInteractions(testLogic, testAutowiredLogic);
    }
}
