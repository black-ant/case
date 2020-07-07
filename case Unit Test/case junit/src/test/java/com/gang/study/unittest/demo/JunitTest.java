package com.gang.study.unittest.demo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @Classname JunitTest
 * @Description TODO
 * @Date 2020/7/6 14:35
 * @Created by zengzg
 */
@DisplayName("JUnit 测试类")
public class JunitTest {

    @BeforeAll
    public static void init() {
        System.out.println("初始化数据");
    }

    @AfterAll
    public static void cleanup() {
        System.out.println("清理数据");
    }

    @BeforeEach
    public void tearup() {
        System.out.println("当前测试方法开始");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("当前测试方法结束");
    }

    @DisplayName("testFirstTest")
    @Test
    void testFirstTest() {
        System.out.println("testFirstTest");
    }

    @DisplayName("测试 @Disabled ")
    @Disabled
    @Test
    void testThirdTest() {
        System.out.println("@Disabled");
    }

}
