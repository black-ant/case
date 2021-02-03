package com.gang.study.source.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

    private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        // 通常我们一般用 applicationContext 的 getBean 获取 bean , 这里的 ConfigurableApplicationContext 实现了 ApplicationContext
        logger.info("------> context :{} <-------", context instanceof ApplicationContext);
    }

    public void buildSelfBanner(String[] args) {
        SpringApplication app = new SpringApplication(MySpringConfiguration.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    // SpringApplication.run(DemoApplication.class, args);
    // ->

}
