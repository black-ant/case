package com.gang.study.spring.demo.logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Classname entity
 * @Description TODO
 * @Date 2020/4/24 10:34
 * @Created by zengzg
 */
@Component
public class TestEntity {

    @Value("${gang.test.server.url}")
    private String ssoServerUrl;

}
