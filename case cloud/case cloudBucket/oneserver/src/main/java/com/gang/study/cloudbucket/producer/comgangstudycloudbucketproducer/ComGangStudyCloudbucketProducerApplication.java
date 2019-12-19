package com.gang.study.cloudbucket.producer.comgangstudycloudbucketproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ComGangStudyCloudbucketProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComGangStudyCloudbucketProducerApplication.class, args);
    }

}
