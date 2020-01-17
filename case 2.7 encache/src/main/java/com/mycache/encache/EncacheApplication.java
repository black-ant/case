package com.mycache.encache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EncacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncacheApplication.class, args);
    }

}
