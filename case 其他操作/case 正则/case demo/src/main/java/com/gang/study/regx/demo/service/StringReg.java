package com.gang.study.regx.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname StringReg
 * @Description 参考 @ https://www.cnblogs.com/javaminer/p/3503389.html
 * @Date 2020/5/20 23:15
 * @Created by zengzg
 */
@Component
public class StringReg implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        matches();

//        matchesNum();

        matchesGroupNum();
    }


    /**
     * Test : Sample
     */
    public void matches() {

        // 测试基础使用
        String pattern = "a.f";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern, "abc"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern, "abf"));
        logger.info("------> this acf is {} <-------", Pattern.matches(pattern, "acf"));
        logger.info("------> this acc is {} <-------", Pattern.matches(pattern, "acc"));


        // 校验是最简单的使用方式 , 只需要按照相关的方式进行匹配即可
        // [abcd] --> 匹配a, b, c, d中的任意一个字符
        String pattern2 = "a[abcd]f";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern2, "abc"));
        logger.info("------> this aef is {} <-------", Pattern.matches(pattern2, "aef"));
        logger.info("------> this acf is {} <-------", Pattern.matches(pattern2, "acf"));
        logger.info("------> this acc is {} <-------", Pattern.matches(pattern2, "acc"));

        // [0123456789] -> 匹配任意一个数字字符
        String pattern3 = "a[0123456789]f";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern3, "abc"));
        logger.info("------> this aef is {} <-------", Pattern.matches(pattern3, "a0f"));
        logger.info("------> this acf is {} <-------", Pattern.matches(pattern3, "acf"));
        logger.info("------> this acc is {} <-------", Pattern.matches(pattern3, "acc"));

        //[0-9a-zA-Z_] -> 任意匹配
        String pattern4 = "a[0-9a-zA-Z_]f";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern4, "abc"));
        logger.info("------> this aef is {} <-------", Pattern.matches(pattern4, "a0f"));
        logger.info("------> this acf is {} <-------", Pattern.matches(pattern4, "acf"));
        logger.info("------> this acc is {} <-------", Pattern.matches(pattern4, "acc"));

        logger.info("------> pattern5 <-------");
        String pattern5 = "a\\sf";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern5, "abf"));
        logger.info("------> this aef is {} <-------", Pattern.matches(pattern5, "a f"));

        logger.info("------> pattern6 <-------");
        String pattern6 = "a\\Sf";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern6, "abf"));
        logger.info("------> this aef is {} <-------", Pattern.matches(pattern6, "a f"));

        logger.info("------> pattern7 <-------");
        String pattern7 = "a\\wf";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern7, "abf"));
        logger.info("------> this aef is {} <-------", Pattern.matches(pattern7, "a f"));

        logger.info("------> pattern8 <-------");
        String pattern8 = "a\\Wf";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern8, "abf"));
        logger.info("------> this aef is {} <-------", Pattern.matches(pattern8, "a f"));

        logger.info("------> pattern9 <-------");
        String pattern9 = "a\\df";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern9, "abf"));
        logger.info("------> this aef is {} <-------", Pattern.matches(pattern9, "a1f"));

        logger.info("------> pattern10 <-------");
        String pattern10 = "a\\Df";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern10, "abf"));
        logger.info("------> this aef is {} <-------", Pattern.matches(pattern10, "a1f"));


        logger.info("------> pattern11 <-------");
        String pattern11 = "^a";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern11, "abf"));
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern11, "abffdsfaaaa"));
        logger.info("------> this aef is {} <-------", Pattern.matches(pattern11, "c1f121312"));
    }

    public void matchesNum() {

        logger.info("------> pattern <-------");
        String pattern = "^a+.*";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern, "abc"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern, "bbf"));

        logger.info("------> pattern <-------");
        String pattern0 = "^a+b?.*";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern0, "ac"));
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern0, "abc"));
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern0, "abbc"));

        logger.info("------> pattern2 <-------");
        String pattern2 = "^a+b{3,5}c*";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern2, "abbc"));
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern2, "abbbc"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern2, "abbbbbc"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern2, "abbbbbbc"));

        logger.info("------> pattern3 <-------");
        String pattern3 = "^a+b{3,}c*";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern3, "abbc"));
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern3, "abbbc"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern3, "abbbbbc"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern3, "abbbbbbc"));

        logger.info("------> pattern4 <-------");
        String pattern4 = "^a+b{3}c*";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern4, "abbc"));
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern4, "abbbc"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern4, "abbbbbc"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern4, "abbbbbbc"));

        logger.info("------> patter5 <-------");
        String patter5 = "^a+b{0,}c*";
        logger.info("------> this abc is {} <-------", Pattern.matches(patter5, "ac"));
        logger.info("------> this abc is {} <-------", Pattern.matches(patter5, "abbbc"));
        logger.info("------> this abf is {} <-------", Pattern.matches(patter5, "abbbbbc"));
    }

    /**
     * 分组功能
     */
    public void matchesGroupNum() {

        /**
         * group 0:http://www.baidu.com
         * group 1:http
         * group 2:www.baidu.com
         *
         * 这里是按照不同的 Group 进行匹配
         */
        logger.info("------> pattern2 <-------");
        Pattern pattern2 = Pattern.compile("(\\w+)://(\\w+\\.\\w+\\.\\w+)");
        String source2 = "http://www.baidu.com";
        Matcher matcher2 = pattern2.matcher(source2);
        if (matcher2.matches()) {
            for (int i = 0; i <= matcher2.groupCount(); i++) {
                System.out.println("group " + i + ":" + matcher2.group(i));
            }
        }


        /**
         * 非捕获分组 -- (?:regex)
         *
         * 这种分组正则表达式引擎不会捕获它所匹配的内容即不会为非捕获型分组分配组号
         */
        logger.info("------> 非捕获分组 <-------");
        Pattern pattern = Pattern.compile("(?:(\\d+))?\\s?([a-zA-Z]+)?.+");
        String source = "2133 fdsdee4333";
        Matcher matcher = pattern.matcher(source);
        if (matcher.matches()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println("group " + i + ":" + matcher.group(i));
            }
        }

        /**
         * 原子分组 -- (?>regex)
         * 原子分组是贪婪的匹配，当文本和这个分组匹配的成功后，正则表达式引擎在匹配后面的表达式时不会发生回溯行为及尽可能多的匹配
         * group-0 : (\d+)
         * group-1 : (?>bc|b)
         *
         */
        logger.info("------>原子分组  <-------");
        Pattern pattern3 = Pattern.compile("(\\d+)\\s+(?>bc|b)(\\w)");
        String source3 = "543543   bcc";  //而“543543   bc” 却匹配失败因为bc已经被原子分组匹配了，当(\\w)进行匹配的时候前面的分组由于是贪婪型匹配所以不会突出以匹配的字符
        Matcher matcher3 = pattern3.matcher(source3);
        if (matcher3.matches()) {
            for (int i = 0; i <= matcher3.groupCount(); i++) {
                System.out.println("group " + i + ":" + matcher3.group(i));
            }
        }

        /**
         * 正前向查找分组（Positive lookahead） -- (?=regex)
         * 在正前向分组里面的表达式匹配成功后，正则表达式引擎回溯到正前向分组开始匹配的字符处再进行后面正则表达式的匹配，如果后面的正则表达式也匹配成功，整个匹配过程才算成功
         *
         */
        logger.info("------>正前向查找分组  <-------");
        Pattern pattern5 = Pattern.compile("(\\d+)\\s+(?=s)(\\w+)");
        String source5 = "543543   streets";        //"543543   ttreets" 匹配失败
        Matcher matcher5 = pattern5.matcher(source5);
        if (matcher5.matches()) {
            for (int i = 0; i <= matcher5.groupCount(); i++) {
                System.out.println("group " + i + ":" + matcher5.group(i));
            }
        }


        /**
         * 负前向查找分组（Negative lookahead） -- (?!regex)
         * 这种分组功能和正前向查找分组一样，唯一的不同就是当前向查找分组里面的正则表达式匹配失败的时候才继续后面的匹配过程
         *
         */
        logger.info("------>负前向查找分组  <-------");
        Pattern pattern6 = Pattern.compile("(\\d+)\\s+(?!s)(\\w+)");
        String source6 = "543543   ttreets";  //如"543543   streets" 匹配失败
        Matcher matcher6 = pattern6.matcher(source6);
        if (matcher6.matches()) {
            for (int i = 0; i <= matcher6.groupCount(); i++) {
                System.out.println("group " + i + ":" + matcher6.group(i));
            }
        }

        /**
         * 正后向查找分组（Positive lookbehind） -- (?<=regex)
         * 可以理解成在正后向查找分组前面的正则表达式匹配成功后，正则表达式引擎从最后的位置往字符串左边进行回溯然后和(?<=regex)进行匹配，
         * 如果匹配失败则整个匹配过程失败；如果匹配成功，则将指针移动到正后向查找分组开始进行匹配的位置继续进行后面正则表达式的匹配过程
         */
        logger.info("------>正后向查找分组  <-------");
        Pattern pattern7 = Pattern.compile("(\\d+)\\s+(?<=\\s)(\\w+)");
        String source7 = "543543   ttreets";  //"543543   streets" 匹配失败
        Matcher matcher7 = pattern7.matcher(source7);
        if (matcher7.matches()) {
            for (int i = 0; i <= matcher7.groupCount(); i++) {
                System.out.println("group " + i + ":" + matcher7.group(i));
            }
        }

        /**
         * 负后向查找分组（Negative lookbehind）  (?<!regex)
         * 这种分组功能和正负向查找分组一样，唯一的不同就是当负后向查找分组里面的正则表达式匹配失败的时候才继续后面的匹配过程
         */
        logger.info("------>负后向查找分组  <-------");
        Pattern pattern8 = Pattern.compile("(\\d+)\\s+(?<!s)(\\w+)");
        String source8 = "543543   ttreets";  //如果正则表达式为(\\d+)\\s+(?<!\\s)(\\w+)则匹配失败
        Matcher matcher8 = pattern8.matcher(source8);
        if (matcher8.matches()) {
            for (int i = 0; i <= matcher8.groupCount(); i++) {
                System.out.println("group " + i + ":" + matcher8.group(i));
            }
        }
    }


    public void replace() {

    }

}
