package com.gang.spi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerDemoApplication {

    /**
     * SPI 启动 :
     *
     * 启动方式 : java -cp ./exchange-rate-api/target/exchange-rate-api-1.0.0-SNAPSHOT.jar:./exchange-rate-app/target/exchange-rate-app-1.0.0-SNAPSHOT.jar com.baeldung.rate.app.MainApp
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerDemoApplication.class, args);
    }

}
