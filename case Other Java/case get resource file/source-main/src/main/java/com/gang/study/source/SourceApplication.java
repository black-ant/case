package com.gang.study.source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname SourceApplication
 * @Description TODO
 * @Date 2020/7/3 14:43
 * @Created by zengzg
 */
@SpringBootApplication
public class SourceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SourceApplication.class, args).getBeanFactory();
    }
}
