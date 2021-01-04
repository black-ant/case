package com.elk.logsystem;

import com.elk.logsystem.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogsystemApplication implements CommandLineRunner {

    @Autowired
    ApplicationService applicationService;

    public static void main(String[] args) {
        SpringApplication.run(LogsystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Logger logger = LoggerFactory.getLogger(LogsystemApplication.class);
        String
        logger.info("测试log");
        for (int i = 0; i < 10; i++) {
            logger.error("测试 something wrong. id={}; name=Ryan-{};", i, i);
        }
    }

}
