package com.gang.study.ibeetl.gangstudyibeetl.service;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartService
 * @Description 快速开始
 * @Date 2021/2/12 21:24
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> Example One , 基础用法一 : 基础替换 <-------");
        sample();

    }

    /**
     * template.render() 返回渲染结果，如本例所示
     * template.renderTo(Writer) 渲染结果输出到Writer里，如果你的Writer是一个FilterWriter，则可把输出保存到文件里
     * template.renderTo(OutputStream) 渲染结果输出到OutputStream里
     * <p>
     * StringTemplateResourceLoader：字符串模板加载器，用于加载字符串模板，如本例所示
     * FileResourceLoader：文件模板加载器，需要一个根目录作为参数构造，传入getTemplate方法的String是模板文件相对于Root目录的相对路径
     * ClasspathResourceLoader：现代web应用最常用的文件模板加载器，模板文件位于Classpath里
     * WebAppResourceLoader：用于webapp集成，假定模板根目录就是WebRoot目录，参考web集成章
     * MapResourceLoader：可以动态存入模板
     * CompositeResourceLoader：混合使用多种加载方式
     *
     * @throws Exception
     */
    public void sample() throws Exception {
        //初始化代码
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        //获取模板
        Template t = gt.getTemplate("hello,${name}");
        t.binding("name", "beetl");
        //渲染结果
        String str = t.render();
        logger.info("------> Example One ,hello,${name}  ->{}  <-------", str);
    }
}
