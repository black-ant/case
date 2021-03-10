package com.gang.study.regx.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname CharTypeReg
 * @Description TODO
 * @Date 2021/3/10 16:09
 * @Created by zengzg
 */
@Component
public class CharTypeReg implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        testP();
    }

    /**
     * 匹配特殊字符
     */
    public void testP() {

        logger.info("------> 匹配 Unicode  , 此处判断是否为 Cyrillic <-------");

        String source = "ДЖem";
        Pattern pattern01 = Pattern.compile("\\P{IsCyrillic}");
        Matcher matcher01 = pattern01.matcher(source);
        while (matcher01.find()) {
            System.out.println(matcher01.group(0));
        }
    }
}
