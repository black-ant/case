package com.gang.elastic.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.gang", "com.gang.common.elastic.repository"})
public class ComGangElasticSearchCaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComGangElasticSearchCaseApplication.class, args);
    }

}
