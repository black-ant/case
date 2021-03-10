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

        // 校验手机号码
//        checkMobile();

        // 校验固定号码
//        checkPhone();

        // 校验日期
//        checkDate();

        // 校验时间
//        checkTime();

        // 校验身份证
//        checkperson();

        // 校验 IP
//        checkip();

        // 校验 URL
//        checkurl();
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
     * 校验手机号码
     */
    public void checkMobile() {

        Pattern pattern = Pattern.compile(
                "(?<![0-9])" // 左边不能有数字
                        + "((0|\\+86|0086)\\s?)?" // 0 +86 0086
                        + "1[34578][0-9]-?[0-9]{4}-?[0-9]{4}" // 186-1234-5678
                        + "(?![0-9])"); // 右边不能有数字
        String source = "15922226666,15833336666";
        Matcher matcher01 = pattern.matcher(source);
        while (matcher01.find()) {
            System.out.println(matcher01.group(0));
        }

    }

    /**
     * 校验固定号码
     */
    public void checkPhone() {
        Pattern pattern = Pattern.compile(
                "(?<![0-9])" // 左边不能有数字
                        + "(\\(?0[0-9]{2,3}\\)?-?)?" // 区号
                        + "[0-9]{7,8}"// 市内号码
                        + "(?![0-9])"); // 右边不能有数字

        String source = "027-333366666,027-66666666";
        Matcher matcher01 = pattern.matcher(source);
        while (matcher01.find()) {
            System.out.println(matcher01.group(0));
        }
    }

    /**
     * 校验时间
     */
    public void checkDate() {

        Pattern pattern = Pattern.compile(
                "(?<![0-9])" // 左边不能有数字
                        + "\\d{4}-" // 年
                        + "(0?[1-9]|1[0-2])-" // 月
                        + "(0?[1-9]|[1-2][0-9]|3[01])"// 日
                        + "(?![0-9])"); // 右边不能有数字
        String source = "2021-01-08,2021-03-6,2021-3-06,2021-08-0601";
        Matcher matcher01 = pattern.matcher(source);
        while (matcher01.find()) {
            logger.info("------> checkData :{} <-------", matcher01.group(0));
        }
    }


    public void checkTime() {
        // 时间
        Pattern pattern = Pattern.compile(
                "(?<![0-9])" // 左边不能有数字
                        + "([0-1][0-9]|2[0-3])" // 小时
                        + ":" + "[0-5][0-9]"// 分钟
                        + "(?![0-9])"); // 右边不能有数字

        String source = "2021010812:08:11,2021010812:8:11,210108:12:0811,20210806:0:1";
        Matcher matcher01 = pattern.matcher(source);
        while (matcher01.find()) {
            logger.info("------> checkTime :{} <-------", matcher01.group(0));
        }
    }

    public void checkperson() {
        // 身份证
        Pattern pattern = Pattern.compile(
                "(?<![0-9])" // 左边不能有数字
                        + "[1-9][0-9]{14}" // 一代身份证
                        + "([0-9]{2}[0-9xX])?" // 二代身份证多出的部分
                        + "(?![0-9])"); // 右边不能有数字
        String source = "420700199503100000,420700199503100001,42070019950310000x";
        Matcher matcher01 = pattern.matcher(source);
        while (matcher01.find()) {
            logger.info("------> checkperson :{} <-------", matcher01.group(0));
        }
    }

    /**
     * 校验IP
     */
    public void checkip() {
        Pattern pattern = Pattern.compile(
                "(?<![0-9])" // 左边不能有数字
                        + "((0{0,2}[0-9]|0?[0-9]{2}|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}"
                        + "(0{0,2}[0-9]|0?[0-9]{2}|1[0-9]{2}|2[0-4][0-9]|25[0-5])"
                        + "(?![0-9])"); // 右边不能有数字

        String source = "123.0.0.1,127.0.0.0.0.1,255,255,255,0";
        Matcher matcher01 = pattern.matcher(source);
        while (matcher01.find()) {
            logger.info("------> checkip :{} <-------", matcher01.group(0));
        }
    }

    public void checkurl() {
        Pattern pattern = Pattern.compile(
                "http://" + "[-0-9a-zA-Z.]+" // 主机名
                        + "(:\\d+)?" // 端口
                        + "(" // 可选的路径和查询 - 开始
                        + "/[-\\w$.+!*'(),%;:@&=]*" // 第一层路径
                        + "(/[-\\w$.+!*'(),%;:@&=]*)*" // 可选的其他层路径
                        + "(\\?[-\\w$.+!*'(),%;:@&=]*)?" // 可选的查询字符串
                        + ")?"); // 可选的路径和查询 - 结束


        String source = "http://www.baidu.com,https://www.baidu.com";
        Matcher matcher01 = pattern.matcher(source);
        while (matcher01.find()) {
            logger.info("------> checkurl :{} <-------", matcher01.group(0));
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
