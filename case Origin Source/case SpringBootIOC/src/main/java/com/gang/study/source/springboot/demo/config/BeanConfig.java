package com.gang.study.source.springboot.demo.config;

import com.gang.study.source.springboot.demo.common.Common2Service;
import com.gang.study.source.springboot.demo.common.CommonService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname BeanConfig
 * @Description TODO
 * @Date 2021/5/11
 * @Created by zengzg
 */
@Configuration
@PropertySource(value = {"classpath:test2.properties"})
public class BeanConfig {

    @Value("${test2.name}")
    private String testName;

    @Value("${test2.id}")
    private Integer testId;

    public Map<String, Object> map2 = new HashMap<>();

    @Bean
    public Map<String, Object> test2() {

        System.out.println("test2 -> | testName -> " + testName + " | test id -> " + testId);
        map2.put("name", testName);
        map2.put("id", testId);
        return map2;
    }

    @Bean(initMethod = "initMethod", autowire = Autowire.BY_TYPE)
    public CommonService commonService() {
        CommonService commonService = new CommonService();
        return commonService;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }
}
