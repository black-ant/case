package com.gang.study.ibeetl.gangstudyibeetl.service;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ClasspathResourceService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
    }


    /**
     * 基本运行类
     */
    public void start() throws Exception {
        logger.info("------> this is run <-------");
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader(" com/gang/study/ibeetl/gangstudyibeetl" +
                ".service");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("/hello.txt");
        t.binding("name", "beetl");
        String str = t.render();
        System.out.println(str);
    }

}
