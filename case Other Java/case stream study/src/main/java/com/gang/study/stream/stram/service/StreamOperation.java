package com.gang.study.stream.stram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @Classname StreamOperation
 * @Description TODO
 * @Date 2021/7/6
 * @Created by zengzg
 */
@Component
public class StreamOperation implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run(ApplicationArguments args) throws Exception {
        test();

        testMap();

    }

    public void test() {
        List<String> randomValues = Arrays.asList(
                "E11", "D12", "A13", "F14", "C15", "A16",
                "B11", "B12", "C13", "B14", "B15", "B16",
                "F12", "E13", "C11", "C14", "A15", "C16",
                "F11", "C12", "D13", "E14", "D15", "D16"
        );
        randomValues.stream()
                .filter(value -> value.startsWith("C"))
                .filter(value -> value.startsWith("C"))
                .filter(value -> value.startsWith("C"))
                .filter(value -> value.startsWith("C"))
                .sorted().forEach(System.out::println);


    }


    public void testMap() {

        List<String> list = Arrays.asList("a", "b", "c");

        List<String> resultList
                = list.stream().map(element -> element.toUpperCase()).collect(Collectors.toList());

        logger.info("------> resultList :{} <-------", resultList);
    }


    public void reduce() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        numbers.stream().reduce((i, j) -> {
            return i * j;
        });


        Arrays.asList(1, 2, 3, 4).stream().sequential();

        List<String> list = Arrays.asList("Mohit", "Nilesh", "Shankar", "Brajesh");
        list.stream().min(Comparator.comparing(String::valueOf))
                .ifPresent(e -> System.out.println("Min: " + e));



    }
}
