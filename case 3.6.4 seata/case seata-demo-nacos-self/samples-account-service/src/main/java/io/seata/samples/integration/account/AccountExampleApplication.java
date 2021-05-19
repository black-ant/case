package io.seata.samples.integration.account;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.seata.samples.integration.account")
@EnableDubbo(scanBasePackages = "io.seata.samples.integration.account")
public class AccountExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountExampleApplication.class, args);
    }

}

