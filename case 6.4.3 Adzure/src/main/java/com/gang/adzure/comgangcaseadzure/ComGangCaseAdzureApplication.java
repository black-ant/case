package com.gang.adzure.comgangcaseadzure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.microsoft", "com.gang"})
public class ComGangCaseAdzureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComGangCaseAdzureApplication.class, args);
    }

}
