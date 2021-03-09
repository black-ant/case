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

//        matchGroup();

//        matchesGroupNum();

        matchesGroupCapture();
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

    public void matchGroup() {
        logger.info("------> pattern <-------");
        String pattern = "^a(bc){2}s$";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern, "abcs"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern, "abcbcs"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern, "abccs"));

        logger.info("------> pattern2 <-------");
        String pattern2 = "^a(b(c)?){2}s$";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern2, "abcs"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern2, "abcbcs"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern2, "abcbs"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern2, "abbs"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern2, "abccs"));


        logger.info("------> pattern3 <-------");
        String pattern3 = "^a(bc|ef|g){2}s$";
        logger.info("------> this abc is {} <-------", Pattern.matches(pattern3, "abcbcs"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern3, "aefs"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern3, "ags"));
        logger.info("------> this abf is {} <-------", Pattern.matches(pattern3, "abcgs"));
    }

    /**
     * 分组功能
     */
    public void matchesGroupNum() {

        // 贪婪匹配
        //        Pattern pattern123 = Pattern.compile(".?o");

        logger.info("------> pattern123 <-------");
        Pattern pattern123 = Pattern.compile(".*?o");
        Matcher matcher123 = pattern123.matcher("zoboco");
        while (matcher123.find()) {
            System.out.println(matcher123.group(0));
        }

//        logger.info("------> pattern1231 <-------");
//        Pattern pattern1231 = Pattern.compile(".*?o");
//        Matcher matcher1231 = pattern1231.matcher("zoboco");
//        System.out.println(matcher1231.group(0));


        /**
         * 匹配基础
         */
        logger.info("------> pattern0 <-------");
        Pattern pattern0 = Pattern.compile("\\w*?\\s");
        String source0 = "aaaa bbbb ccco";
        Matcher matcher0 = pattern0.matcher(source0);
        // 判断是否还有
        while (matcher0.find()) {
            System.out.println("group " + ":" + matcher0.group(0));
        }

        /**
         * 分组基础
         */
        logger.info("------> pattern01 <-------");
        Pattern pattern01 = Pattern.compile("(\\w)*?\\s");
        String source01 = "aaaa bbbb ccco";
        Matcher matcher01 = pattern01.matcher(source01);
        // 判断是否还有

        while (matcher01.find()) {
            System.out.println("group " + ":" + matcher01.group(0));
        }


        logger.info("------> pattern02 <-------");
        Pattern pattern02 = Pattern.compile("((\\w)*?\\s)");
        String source02 = "aaaa bbbb ccco";
        Matcher matcher02 = pattern02.matcher(source02);
        // 判断是否还有
        for (int i = 0; i <= matcher02.groupCount(); i++) {
            while (matcher02.find()) {
                System.out.println("group " + ":" + matcher02.group(i));
            }
        }


        /**
         * 捕获基础
         */
        logger.info("------> pattern 捕获  <-------");
        Pattern pattern03 = Pattern.compile("(a+)\\s(b+)\\s(c+)");
        String source03 = "aaaa bbbb cccc";
        Matcher matcher03 = pattern03.matcher(source03);
        // 判断是否还有
        logger.info("------> this abc is {} <-------", Pattern.matches("(a+)\\s(b+)\\s(c+)", source03));
        if (matcher03.matches()) {
            System.out.println("group 0" + ":" + matcher03.group(0));
            System.out.println("group 1" + ":" + matcher03.group(1));
            System.out.println("group 2" + ":" + matcher03.group(2));
            System.out.println("group 3" + ":" + matcher03.group(2));
        }


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


    }


    public void matchesGroupCapture() {
        /**
         * 非捕获分组 -- (?:regex)
         *
         * 这种分组正则表达式引擎不会捕获它所匹配的内容即不会为非捕获型分组分配组号
         */
        logger.info("------> 非捕获分组 (?:(\\d+))?\\s?([a-zA-Z]+)?.+ <-------");
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
         * group-1 : (\d+)
         * group-2 : (?>bc|b)
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
         * 从效果上说 , 原子分组
         */
        logger.info("------>原子分组对比  <-------");
        Pattern pattern3_1 = Pattern.compile("(\\d+)\\s+(bc|b)(\\w)");
        String source3_1 = "543543   bcc";
        Matcher matcher3_1 = pattern3_1.matcher(source3_1);
        if (matcher3_1.matches()) {
            for (int i = 0; i <= matcher3_1.groupCount(); i++) {
                System.out.println("group " + i + ":" + matcher3_1.group(i));
            }
        }

        // 原子匹配的特定 : 不回溯 , 所以只需要 bc 后面的匹配 a 就行
        Pattern pattern3_2 = Pattern.compile("(\\d+)\\s+(?>bc|b)(a)");
        String source3_2 = "543543   bca";  //而“543543   bc” 却匹配失败因为bc已经被原子分组匹配了，当(\\w)进行匹配的时候前面的分组由于是贪婪型匹配所以不会突出以匹配的字符
        Matcher matcher3_2 = pattern3_2.matcher(source3_2);
        if (matcher3_2.matches()) {
            logger.info("------> 原子分组 匹配成功 :{} <-------",source3_2);
        }else{
            logger.info("------> 原子分组 匹配失败 :{}<-------",source3_2);
        }

        // b 已经被匹配走了
        Pattern pattern3_3 = Pattern.compile("(\\d+)\\s+(?>b)(bca)");
        String source3_3 = "543543   bca";  //而“543543   bc” 却匹配失败因为bc已经被原子分组匹配了，当(\\w)进行匹配的时候前面的分组由于是贪婪型匹配所以不会突出以匹配的字符
        Matcher matcher3_3 = pattern3_3.matcher(source3_3);
        if (matcher3_3.matches()) {
            logger.info("------> 原子分组 匹配成功 :{} <-------",source3_3);
        }else{
            logger.info("------> 原子分组 匹配失败 :{}<-------",source3_3);
        }


        /**
         * 正前向查找分组（Positive lookahead） -- (?=regex)
         * 在正前向分组里面的表达式匹配成功后，正则表达式引擎回溯到正前向分组开始匹配的字符处再进行后面正则表达式的匹配，如果后面的正则表达式也匹配成功，整个匹配过程才算成功
         *
         * 回溯性 : 即匹配后续字符会包含分组匹配的字符
         * 其实该方式和正常匹配区别不大
         *
         * Step 1 : (\d+)\s+ 匹配到空格最后一位 -- 543543
         * Step 2 : (?=s)(\w+) 匹配到字符 s 开头的字符串
         *
         */
        logger.info("------>正前向查找分组  <-------");
        Pattern pattern5 = Pattern.compile("(\\d+)\\s+(?=s)(\\w+)");
        String source5 = "543543   streets";
        Matcher matcher5 = pattern5.matcher(source5);
        // 先找到坐标 s , s前面得符合空格 , s 后面为字母
        if (matcher5.matches()) {
            logger.info("------> 正前向查找分组 匹配成功 :{} <-------",source5);
        }else{
            logger.info("------> 正前向查找分组 匹配失败 :{}<-------",source5);
        }

        // 匹配失败 :
        String source5_1 = "543543   ttseett";
        Matcher matcher5_1 = pattern5.matcher(source5_1);
        if (matcher5_1.matches()) {
            logger.info("------> 正前向查找分组 匹配成功 :{} <-------",matcher5_1);
        }else{
            logger.info("------> 正前向查找分组 匹配失败 :{}<-------",matcher5_1);
        }

        Pattern pattern5_2 = Pattern.compile("(\\d+)\\s+tt(?=r)(\\w+)");
        String source5_2 = "543543   ttreets";
        Matcher matcher5_2 = pattern5_2.matcher(source5_2);
        if (matcher5_2.matches()) {
            logger.info("------> 正前向查找分组 匹配成功 :{} <-------",source5_2);
        }else{
            logger.info("------> 正前向查找分组 匹配失败 :{}<-------",source5_2);
        }

        // 先匹配到 543543   ttr 后面的空格 , 但是含空格的字符不匹配 \w+
        Pattern pattern5_3 = Pattern.compile("(\\d+)\\s+(?=1)(\\w+)");
        String source5_3 = "543543   1tccc";
        Matcher matcher5_3 = pattern5_3.matcher(source5_3);
        if (matcher5_3.matches()) {
            logger.info("------> 正前向查找分组 匹配成功 :{} <-------",source5_3);
        }else{
            logger.info("------> 正前向查找分组 匹配失败 :{}<-------",source5_3);
        }

        /**
         * 负前向查找分组（Negative lookahead） -- (?!regex)
         * 这种分组功能和正前向查找分组一样，唯一的不同就是当前向查找分组里面的正则表达式匹配失败的时候才继续后面的匹配过程
         * Step 1 : 按照负前分组前得坐标进行匹配 -> 543543   <- 即 前面数字 , 后面空格
         * Step 2 : 匹配后续是否为 s , 不为!! s 则匹配成功
         * Step 3 : 确定后续的为字母
         */
        logger.info("------>负前向查找分组  <-------");
        Pattern pattern6 = Pattern.compile("(\\d+)\\s+(?!s)(\\w+)");
        String source6 = "543543   ttreets";  //如"543543   streets" 匹配失败
        Matcher matcher6 = pattern6.matcher(source6);
        if (matcher6.matches()) {
            logger.info("------> 负前向查找分组 匹配成功 :{} <-------",source6);
        }else{
            logger.info("------> 负前向查找分组 匹配失败 :{}<-------",source6);
        }

        // 因为前匹配成功后第一个 是 s
        String source6_1 = "543543   streets";
        Matcher matcher6_1 = pattern6.matcher(source6_1);
        if (matcher6_1.matches()) {
            logger.info("------> 负前向查找分组 匹配成功 :{} <-------",source6_1);
        }else{
            logger.info("------> 负前向查找分组 匹配失败 :{}<-------",source6_1);
        }


        /**
         * 正后向查找分组（Positive lookbehind） -- (?<=regex)
         * 可以理解成在正后向查找分组前面的正则表达式匹配成功后，正则表达式引擎从最后的位置往字符串左边进行回溯然后和(?<=regex)进行匹配，
         * 如果匹配失败则整个匹配过程失败；如果匹配成功，则将指针移动到正后向查找分组开始进行匹配的位置继续进行后面正则表达式的匹配过程
         *
         * Step 1 : (\d+)\s+ 进行匹配 , 匹配到最后一个空格 -- 66666
         * Step 2 : 反向匹配 (\w+) , 匹配到 abttts
         * Step 3 : 此时 abttts 前面不是 s ,所以匹配失败
         *
         *
         */
        logger.info("------>正后向查找分组  <-------");
        Pattern pattern7 = Pattern.compile("(\\d+)\\s+(?<=s)(\\w+)");
        String source7 = "66666   abttts";
        Matcher matcher7 = pattern7.matcher(source7);
        if (matcher7.matches()) {
            logger.info("------> 正后向查找分组 匹配成功 :{} <-------",source7);
        }else{
            logger.info("------> 正后向查找分组 匹配失败 :{}<-------",source7);
        }

        // 匹配成功 , 因为 sbttttt 前面是空格
        Pattern pattern7_1 = Pattern.compile("(\\d+)\\s+(?<=\\s)(\\w+)");
        String source7_1 = "66666   sbttttt";
        Matcher matcher7_1 = pattern7_1.matcher(source7_1);
        if (matcher7_1.matches()) {
            logger.info("------> 正后向查找分组 匹配成功 :{} <-------",source7_1);
        }else{
            logger.info("------> 正后向查找分组 匹配失败 :{}<-------",source7_1);
        }


        Pattern pattern7_2 = Pattern.compile("(\\d+)\\s+(?<=\\s)b(\\w+)");
        String source7_2 = "66666   sbttttt";
        Matcher matcher7_2 = pattern7_2.matcher(source7_2);
        if (matcher7_2.matches()) {
            logger.info("------> 正后向查找分组 匹配成功 :{} <-------",source7_2);
        }else{
            logger.info("------> 正后向查找分组 匹配失败 :{}<-------",source7_2);
        }

        // 扩展 , 能看出为什么成功了吗?
        Pattern pattern7_3 = Pattern.compile("(\\d+)\\s+s(?<=s)b(\\w+)");
        String source7_3 = "66666   sbttttt";
        Matcher matcher7_3 = pattern7_3.matcher(source7_2);
        if (matcher7_3.matches()) {
            logger.info("------> 正后向查找分组 匹配成功 :{} <-------",source7_3);
        }else{
            logger.info("------> 正后向查找分组 匹配失败 :{}<-------",source7_3);
        }

        /**
         * 负后向查找分组（Negative lookbehind）  (?<!regex)
         * 这种分组功能和正负向查找分组一样，唯一的不同就是当负后向查找分组里面的正则表达式匹配失败的时候才继续后面的匹配过程
         *
         * Step 1 : (\d+)\s+ 匹配 543543   , 即最后一个空格
         * Step 2 : 反向匹配 (\w+) , 匹配到 ttreets
         * Step 3 : 此时 t 首字母前面不为 s , 匹配成功
         *
         */
        logger.info("------>负后向查找分组  <-------");
        Pattern pattern8 = Pattern.compile("(\\d+)\\s+(?<!s)(\\w+)");
        String source8 = "543543   ttreets";  //如果正则表达式为(\\d+)\\s+(?<!\\s)(\\w+)则匹配失败
        Matcher matcher8 = pattern8.matcher(source8);
        if (matcher8.matches()) {
            logger.info("------> 负后向查找分组 匹配成功 :{} <-------",source8);
        }else{
            logger.info("------> 负后向查找分组 匹配失败 :{}<-------",source8);
        }

        // 此时 c(\w+) 匹配到的是 creets , 前面是s ,所以匹配失败
        Pattern pattern8_1 = Pattern.compile("(\\d+)\\s+(?<!s)c(\\w+)");
        String source8_1 = "543543   screets";
        Matcher matcher8_1 = pattern8_1.matcher(source8_1);
        if (matcher8_1.matches()) {
            logger.info("------> 负后向查找分组 匹配成功 :{} <-------",matcher8_1);
        }else{
            logger.info("------> 负后向查找分组 匹配失败 :{}<-------",matcher8_1);
        }

        Pattern pattern8_2 = Pattern.compile("(\\d+)\\s+(?<!\\s)(\\w+)");
        String source8_2 = "543543   streets";
        Matcher matcher8_2 = pattern8_2.matcher(source8_2);
        if (matcher8_2.matches()) {
            logger.info("------> 负后向查找分组 匹配成功 :{} <-------",matcher8_2);
        }else{
            logger.info("------> 负后向查找分组 匹配失败 :{}<-------",matcher8_2);
        }
    }

}
