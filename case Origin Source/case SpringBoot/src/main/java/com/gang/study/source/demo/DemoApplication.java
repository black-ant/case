package com.gang.study.source.demo;

import com.gang.study.source.demo.source.SpringApplicationSource;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplicationSource.run(DemoApplication.class, args);
    }

}
