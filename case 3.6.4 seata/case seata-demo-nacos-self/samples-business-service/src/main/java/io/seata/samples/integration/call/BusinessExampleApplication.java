package io.seata.samples.integration.call;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "io.seata.samples.integration.call", exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo(scanBasePackages = "io.seata.samples.integration.call")
public class BusinessExampleApplication {

    /**
     * 127.0.0.1:8104//business/dubbo/buy
     * 127.0.0.1:8104//business/dubbo/buy2
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BusinessExampleApplication.class, args);
    }

}

