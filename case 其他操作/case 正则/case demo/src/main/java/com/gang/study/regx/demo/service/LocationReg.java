package com.gang.study.regx.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname LocationReg
 * @Description TODO
 * @Date 2021/3/10 14:39
 * @Created by zengzg
 */
@Component
public class LocationReg implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        start();
    }

    public void start() {

        String source1 = "99999 2323 3434-34234";
        Pattern pattern1 = Pattern.compile("^\\d{3}");
        Matcher matcher1 = pattern1.matcher(source1);
        while (matcher1.find()) {
            logger.info("------> ^ source [{}]  group [{}]<-------", source1, matcher1.group(0));
        }

        String source2 = "99999 23233232-32232";
        Pattern pattern2 = Pattern.compile("\\A\\d{3}");
        Matcher matcher2 = pattern2.matcher(source2);
        while (matcher2.find()) {
            logger.info("------> \\A source [{}]  group [{}]<-------", source2, matcher2.group(0));
        }

        // 末尾匹配
        String source3 = "99999 23233232-32232";
        Pattern pattern3 = Pattern.compile("\\d{3}\\Z}");
        Matcher matcher3 = pattern3.matcher(source3);
        while (matcher3.find()) {
            logger.info("------> \\Z source [{}]  group [{}]<-------", matcher3, matcher3.group(0));
        }

        // 末尾匹配
        String source4 = "99999 23233434-34234";
        Pattern pattern4 = Pattern.compile("\\d{3}\\z");
        Matcher matcher4 = pattern4.matcher(source4);
        while (matcher4.find()) {
            logger.info("------> 末尾匹配 source [{}]  group [{}]<-------", source4, matcher4.group(0));
        }

        // 上一个匹配结束的地方
        String source5 = "(1)(3)(5)[7](9)";
        Pattern pattern5 = Pattern.compile("\\G\\(\\d\\)");
        Matcher matcher5 = pattern5.matcher(source5);
        while (matcher5.find()) {
            logger.info("------> 匹配结束 source [{}]  group [{}]<-------", source5, matcher5.group(0));
        }

        // 字符边界 , 以下正则表示 字母之前 和字母之后
        String source6 = "them theme them them";
        Pattern pattern6 = Pattern.compile("\\b\\w+\\s\\w+\\b");
        Matcher matcher6 = pattern6.matcher(source6);
        while (matcher6.find()) {
            logger.info("------> 字符边界 source [{}]  group [{}]<-------", source6, matcher6.group(0));
        }


        // 非字符边界 , 以下正则表示 字母之前 和字母之后
        String source7 = "end sends endure lender";
        Pattern pattern7 = Pattern.compile("\\Bend\\w*\\b");
        Matcher matcher7 = pattern7.matcher(source7);
        while (matcher7.find()) {
            logger.info("------> 非字符边界 source [{}]  group [{}]<-------", source7, matcher7.group(0));
        }
    }
}
