package com.gang.study.stream.stram;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Classname StreamFunctionTest
 * @Description TODO
 * @Date 2021/7/7
 * @Created by zengzg
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamFunctionTest {


    @Test
    public void test() {
        List<String> list = Arrays.asList("B", "A", "C", "D", "F");
        list.stream().forEach(System.out::println);

    }

    public String testFunction(String key) {
        return key + "0001";
    }
}
