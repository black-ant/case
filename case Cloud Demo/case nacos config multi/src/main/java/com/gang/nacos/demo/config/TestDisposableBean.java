package com.gang.nacos.demo.config;

import org.springframework.beans.factory.DisposableBean;

public class TestDisposableBean implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("[DisposableBean] NormalBeanA");
    }
}
