package com.gang.study.regx.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname SampleRaf
 * @Description TODO
 * @Date 2021/3/10 14:09
 * @Created by zengzg
 */
@Component
public class SampleRag implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        characterEscape();

        // 转义字符匹配空格
//        test002();
    }


    public void characterEscape() {
        String pattern4 = "\\a";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern4, "Error!\\a"));


    }

    /**
     * 转义字符匹配空格
     */
    public void test002() {

        // \\040 八进制匹配空格 \ nnn
        String source = "a bc d";
        Pattern pattern01 = Pattern.compile("\\w\\040\\w");
        Matcher matcher01 = pattern01.matcher(source);
        while (matcher01.find()) {
            System.out.println(matcher01.group(0));
        }

        // 十六进制 Unicode \\u nnnn
        String source2 = "a bc d";
        Pattern pattern2 = Pattern.compile("\\w\\u0020\\w");
        Matcher matcher2 = pattern2.matcher(source2);
        while (matcher2.find()) {
            logger.info("------> 十六进制 Unicode  source [{}]  group [{}]<-------", source2, matcher2.group(0));
        }

        // 十六进制 -- \x nn
        String source3 = "a bc d";
        Pattern pattern3 = Pattern.compile("\\w\\x20\\w");
        Matcher matcher3 = pattern3.matcher(source3);
        while (matcher3.find()) {
            logger.info("------> 十六进制 source [{}]  group [{}]<-------", source3, matcher3.group(0));
        }

        // ASCII -- 测试不可用 , 待确定
        String source4 = "\\cC";
        Pattern pattern4 = Pattern.compile("\\x0003");
        Matcher matcher4 = pattern4.matcher(source4);
        while (matcher4.find()) {
            logger.info("------> ASCII source [{}]  group [{}]<-------", source4, matcher4.group(0));
        }


    }


}
