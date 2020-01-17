package com.gang.study.reflect.javareflect.service;

import com.gang.study.reflect.javareflect.entity.CommonTestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Date 2019 - 09 - 10
 * @Version 1.0
 * @Author ant-black 1016930479@qq.com
 */
@Service
public class ReflectService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * description : this method main test instance single question
     */
    public void reflectGetEntity() {
        logger.info("------> this is in reflectGetEntity <-------");
        try {
            String classPath = "com.gang.study.reflect.javareflect.entity.CommonTestEntity";

            Class clazz = Class.forName(classPath);

            CommonTestEntity testEntityOne = (CommonTestEntity) clazz.newInstance();
            testEntityOne.setAge("18");
            testEntityOne.setUsername("ant-black");
            logger.info("------> username is {} -- age :{} <-------", testEntityOne.getUsername(), testEntityOne.getAge());

            CommonTestEntity testEntityTwo = (CommonTestEntity) clazz.newInstance();
            testEntityTwo.setAge("888");
            logger.info("------> username is {} -- age :{} <-------", testEntityTwo.getUsername(), testEntityTwo.getAge());
            logger.info("------> testEntityOne username is {} -- age :{} <-------", testEntityOne.getUsername(), testEntityOne.getAge());


            CommonTestEntity testEntityThree = (CommonTestEntity) clazz.newInstance();
            testEntityThree.setUsername("ant-black-test");
            logger.info("------> username is {} -- age :{} <-------", testEntityThree.getUsername(), testEntityThree.getAge());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
