package com.gang.study.elk.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ComGangStudyElkApplication {

    private final static Logger logger = LoggerFactory.getLogger(ComGangStudyElkApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ComGangStudyElkApplication.class, args);
    }

    @GetMapping("/{name}")
    public String hi(@PathVariable(value = "name") String name) {
        logger.info("name = {}", name);
        return "hi , " + name;
    }


    @GetMapping("/test1")
    public String test() {
        logger.info("你好啊e");
        logger.warn("This is a warn message!");
        logger.error("This is error message!");
        return "server被调用了！:";
    }

}
