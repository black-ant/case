package com.gang.demo;

import com.gang.demo.config.LineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ClassConfigurationApplication {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LineConfiguration lineConfiguration;

    @Value("${gang.name}")
    private String name;
    @Value("${gang.url}")
    private String url;

    @RequestMapping("/index")
    public String index() {
        return "welcome " + name + "springBoot" + ":" + url;
    }

    @GetMapping("/line")
    public String getLine() {
        logger.info("------> this is in line :{}<-------", lineConfiguration.getCommand());
        return lineConfiguration.getCommand();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClassConfigurationApplication.class, args);
    }


    /**
     * $ 模式加载顺序
     *  1 > --LINE_COMMAND=ARGS
qi     */
}
