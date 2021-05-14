package com.gang.study.sharding.boot.demo;

//import com.gang.study.sharding.boot.demo.config.TransactionConfig;
import com.gang.study.sharding.boot.demo.config.TransactionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TransactionConfig.class)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
