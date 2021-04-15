package com.gang.study.source.springboot.demo;

import com.gang.study.source.springboot.demo.common.OtherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource(locations = {"classpath:spring-common.xml"})
public class DemoApplication {


    /**
     * 操作记录 :
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public OtherService getOtherService() {
        return new OtherService();
    }

}
