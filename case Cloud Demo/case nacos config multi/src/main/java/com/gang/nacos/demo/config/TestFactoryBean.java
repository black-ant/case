package com.gang.nacos.demo.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class TestFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        System.out.println("[FactoryBean] getObject");
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
