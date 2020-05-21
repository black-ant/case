package com.gang.study.regx.demo.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @Classname StringReg
 * @Description TODO
 * @Date 2020/5/20 23:15
 * @Created by zengzg
 */
@Component
public class StringReg implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String regex = "<(\\w+)>(.*)</\\1>";
        Pattern pattern = Pattern.compile(regex);

    }
}
