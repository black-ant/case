package com.gang.study.stream.stram;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * stream 流集合转换类
 */
public class ExchangeSteam {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public <T extends Collection> void exchangeToSet(T steamData) {

        Set<Object> setDate = Stream.of(steamData).collect(Collectors.toCollection(TreeSet::new));

        setDate.forEach(p -> {
            logger.info("is in stram");
            logger.info("p is :{}", p);
        });
    }

    /**
     * 转换为数组
     *
     * @param t
     * @param <T>
     */
    public <T extends Collection> void exchangeToArray(T t) {
        String[] array = (String[]) t.stream().toArray(String[]::new);
        logger.info(array.toString());
    }


    public <T extends Collection> void exchangeToCollect(T t) {

        t.stream().collect(Collectors.toList());

        t.stream().collect(Collectors.toCollection(ArrayList::new));

        t.stream().collect(Collectors.toSet());

        t.stream().collect(Collectors.toCollection(Stack::new));
    }

    public <T extends Collection> void exchangeToString(T t) {

        logger.info(t.stream().collect(Collectors.joining()).toString());
        t.stream().collect(Collectors.joining(" | "));
        t.stream().collect(Collectors.joining(" || ", "Start--", "--End"));
    }
}
