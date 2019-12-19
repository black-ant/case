package com.gang.study.cloudbucket.twoserver.comgangstudycloudbuckettwoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ComGangStudyCloudbucketTwoserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComGangStudyCloudbucketTwoserverApplication.class, args);
    }

}
