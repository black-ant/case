package com.gang.comgangcasedwr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


/**
 *
 */
@SpringBootApplication
@ImportResource(locations = "classpath:dwr-spring-config.xml")
public class ComGangCaseDwrApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComGangCaseDwrApplication.class, args);
    }

}
