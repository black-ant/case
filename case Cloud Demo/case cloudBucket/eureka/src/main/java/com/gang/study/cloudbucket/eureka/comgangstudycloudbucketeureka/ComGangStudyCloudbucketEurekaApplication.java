package com.gang.study.cloudbucket.eureka.comgangstudycloudbucketeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ComGangStudyCloudbucketEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComGangStudyCloudbucketEurekaApplication.class, args);
    }

}
