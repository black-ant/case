package com.gang.study.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname bootdependency
 * @Description TODO
 * @Date 2019/12/18 10:32
 * @Created by zengzg
 */
@SpringBootApplication
public class BootdependencyApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BootdependencyApplication.class, args).getBeanFactory();
    }
}
