package com.gang.study.regx.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname CommonlyUsedReg
 * @Description 常用
 * @Date 2021/3/9 13:57
 * @Created by zengzg
 */
@Service
public class CommonlyUsedReg implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 匹配密码复杂度
//        checkPassword();

        // 匹配邮箱
//        checkEmail();

        // 匹配中文
//        checkChiness();

        // 获取邮箱类型
//        getEmailType();
    }

    /**
     * 校验密码长度
     */
    public void checkPassword() {

        //
        String pattern4 = "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$";

        logger.info("------> this abc is {} <-------", Pattern.matches(pattern4, "abbc"));
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern4, "abbc"));
    }

    /**
     * 邮箱规则 :
     * 1 @ 之前不能是 @ 和 空格
     */
    public void checkEmail() {
        String pattern = "^[^\\s|^@]+@[^\\s|^@]+.[com|cn]+";
        logger.info("------> this is email {} <-------", Pattern.matches(pattern, "1016@qq.com"));
        logger.info("------> this is email {} <-------", Pattern.matches(pattern, "10@16@qq.com"));
        logger.info("------> this is email {} <-------", Pattern.matches(pattern, "1016@qq.org"));
        logger.info("------> this is email {} <-------", Pattern.matches(pattern, "1016123qq.com"));
    }

    /**
     * 匹配中文
     */
    public void checkChiness() {
        String source = "12412412中文12312412第二个4124";
        Pattern pattern01 = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher matcher01 = pattern01.matcher(source);
        while (matcher01.find()) {
            System.out.println(matcher01.group(0));
        }
    }


    /**
     * 获取邮箱类型
     */
    public void getEmailType() {
        String source = "123@qq.com,124@qq.com,125@qq.com,126@qq.com,128@163.com,129@163.com,130@188.com";
        // [^@] -> 非 @ 字符
        // , -> 逗号结尾
        // ? -> 最小匹配
        Pattern pattern01 = Pattern.compile("([^@]+)?@([^@]+)?,");
        Matcher matcher01 = pattern01.matcher(source);
        List<String> userList = new ArrayList<>();
        List<String> emailList = new ArrayList<>();
        while (matcher01.find()) {
            System.out.println(matcher01.group(0));
            System.out.println(matcher01.group(1));
            userList.add(matcher01.group(1));
            System.out.println(matcher01.group(2));
            emailList.add(matcher01.group(2));
        }

        logger.info("------> 邮箱用户有 : {} <-------", userList.toString());
        logger.info("------> 邮箱类型有 : {} <-------", emailList.toString());

    }


}
