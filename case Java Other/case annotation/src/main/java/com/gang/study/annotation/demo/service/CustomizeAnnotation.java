package com.gang.study.annotation.demo.service;

import com.gang.study.annotation.demo.annotation.UserAnno;
import com.gang.study.annotation.demo.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomizeAnnotation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void getAnnoValue(UserModel userModel) {
        Annotation[] userAnno = userModel.getClass().getDeclaredAnnotations();
        List<Field> list = Arrays.asList(userModel.getClass().getDeclaredFields());
        for (int i = 0; i < list.size(); i++) {
            logger.info("--11----------> {}", list.get(i).getDeclaredAnnotations());
        }

        logger.info("---22--------->{}", userAnno);

    }
}
