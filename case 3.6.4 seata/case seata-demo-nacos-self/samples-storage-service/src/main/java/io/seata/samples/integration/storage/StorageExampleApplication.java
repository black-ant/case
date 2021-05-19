package io.seata.samples.integration.storage;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.seata.samples.integration.storage")
@EnableDubbo(scanBasePackages = "io.seata.samples.integration.storage")
public class StorageExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageExampleApplication.class, args);
    }

}

