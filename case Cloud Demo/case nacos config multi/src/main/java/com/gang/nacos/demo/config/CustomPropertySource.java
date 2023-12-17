package com.gang.nacos.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomPropertySource implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("进入 EnvironmentPostProcessor 初始化逻辑");
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        // 创建一个属性源 , 从 Nacos 中拉取数据
        Map<String, Object> sourceMap = new HashMap<>();

        // 把拉取的数据写入对应的 Map
        MapPropertySource propertySource = new MapPropertySource("mySource", sourceMap);

        // 丢到 environment 中
        environment.getPropertySources().addFirst(propertySource);
    }
}
