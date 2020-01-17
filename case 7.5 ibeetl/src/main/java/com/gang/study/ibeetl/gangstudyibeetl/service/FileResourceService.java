package com.gang.study.ibeetl.gangstudyibeetl.service;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileResourceService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 基本运行类
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is run <-------");
        try {
            main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void main() throws IOException {
        String root = System.getProperty("user.dir") + File.separator + "template";
        FileResourceLoader resourceLoader = new FileResourceLoader(root, "utf-8");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("/hello.txt");
        String str = t.render();
        logger.info("------> str is :{} <-------", str);
    }
}
