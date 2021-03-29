package org.mengyun.tcctransaction.sample.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = {"org.mengyun"})
@EnableDubbo
public class CapitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapitalApplication.class, args);
    }

}
