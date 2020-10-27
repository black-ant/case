package com.gang.string.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StringFormatTest
 * @Description TODO
 * @Date 2020/10/26 15:20
 * @Created by zengzg
 */
@Component
public class StringFormatTest implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        stringAdd();
        stringFromat();

        stringFromat();
        stringAdd();

        stringAdd();
        stringFromat();
    }

    public void stringFromat() {
        Long start = System.currentTimeMillis();
        String str = "";
        String strFormat = "TestSte%s";
        for (int i = 0; i < 30000; i++) {
            str = String.format(strFormat, i);
        }
        Long end = System.currentTimeMillis();
        logger.info("------> this is end stringBuild :{}-{} : {} <-------", start, end, end - start);
    }

    public void stringAdd() {
        Long start = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < 30000; i++) {
            str = "TestSte" + i;
        }
        Long end = System.currentTimeMillis();
        logger.info("------> this is end stringBuild :{}-{} : {} <-------", start, end, end - start);
    }
}
