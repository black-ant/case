package com.gang.study.reflect.javareflect.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname AnnotationService
 * @Description TODO
 * @Date 2020/11/12 13:47
 * @Created by zengzg
 */
@Component
public class AnnotationService implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

    /**
     * 反射获取标注了指定注解的类
     */
    public void reflectAnnoctionClass() {

    }
}
