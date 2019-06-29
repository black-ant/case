package com.gang.study.stream.stram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class CreateStream {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public <T extends Collection> void collectToStream(T collect) {

        // stream 方法
        Stream stram = collect.stream();
        stram.forEach(p -> {
            logger.info("is in stram");
            logger.info("p is :{}", p);
        });

        // parallelStream 方法
        Stream stram2 = collect.parallelStream();
        stram2.forEach(p -> {
            logger.info("is in parallelStream");
            logger.info("parallelStream p is :{}", p);
        });

        // Stream.of 方法
        Stream stram3 = Stream.of(collect);
        stram3.forEach(p -> {
            logger.info("is in stram of");
            logger.info("of p is :{}", p);
        });
    }

    public void arrayToStream(String[] array) {
        // Stream.of 方法
        Stream stram = Arrays.stream(array);
        stram.forEach(p -> {
            logger.info("is in array");
            logger.info("p is :{}", p);
        });
    }
}
