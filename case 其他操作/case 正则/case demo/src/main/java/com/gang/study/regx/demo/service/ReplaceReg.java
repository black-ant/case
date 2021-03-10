package com.gang.study.regx.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname ReplaceReg
 * @Description TODO
 * @Date 2021/3/10 16:59
 * @Created by zengzg
 */
@Component
public class ReplaceReg implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        replaceSample();

//        replaceByName();

//        replace$();

//        replaceLastGroup();
    }

    /**
     * 替换基础用法
     */
    public void replaceSample() {
        String source = "Doe, John";
        Pattern pattern1 = Pattern.compile("(\\w+),\\s*(\\w+)");
        Matcher matcher1 = pattern1.matcher(source);
        String response = matcher1.replaceAll("$2 $1");
        logger.info("------> this is replaceSample response :{} <-------", response);
    }


    /**
     * 替换基础用法
     */
    public void replaceByName() {
        String source = "one two";
        Pattern pattern1 = Pattern.compile("\\b(?<word1>\\w+)(\\s)(?<word2>\\w+)\\b");
        Matcher matcher1 = pattern1.matcher(source);
        String response = matcher1.replaceAll("${word2} ${word1}");
        logger.info("------> this is replaceByName response :{} <-------", response);
    }

    /**
     * 替换 $ 字符
     */
    public void replace$() {
        String source = "103 USD";
        Pattern pattern1 = Pattern.compile("(\\d+)\\s?USD");
        Matcher matcher1 = pattern1.matcher(source);
        String response = matcher1.replaceAll("\\$$1");
        logger.info("------> this is replaceByName response :{} <-------", response);
    }


    /**
     * 替换 最后捕获的组
     * 测试 $' $+ 在Java 中不适合 , 待分析
     */
    public void replaceLastGroup() {
        String source = "AABBCCDD";
        Pattern pattern1 = Pattern.compile("B+(C+)");
        Matcher matcher1 = pattern1.matcher(source);
        String response = matcher1.replaceAll("$'");
        logger.info("------> this is replaceByName response :{} <-------", response);
    }
}
