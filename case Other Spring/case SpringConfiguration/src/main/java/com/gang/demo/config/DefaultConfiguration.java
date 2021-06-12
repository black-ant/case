package com.gang.demo.config;

import com.gang.demo.entity.ConfigEntity;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Classname ClassConfiguration
 * @Description TODO
 * @Date 2020/4/28 22:23
 * @Created by zengzg
 */
@Configuration
@ConfigurationProperties(prefix = "gang.test")
@Component
public class DefaultConfiguration {

    private String name;
    private Integer age;
    private List<String> testList;
    private List<ConfigEntity> cfgEntities;
    private Map<String, String> cfgMap;
    private Map<String, ConfigEntity> cfgEntityMap;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getTestList() {
        return testList;
    }

    public void setTestList(List<String> testList) {
        this.testList = testList;
    }

    public List<ConfigEntity> getCfgEntities() {
        return cfgEntities;
    }

    public void setCfgEntities(List<ConfigEntity> cfgEntities) {
        this.cfgEntities = cfgEntities;
    }

    public Map<String, String> getCfgMap() {
        return cfgMap;
    }

    public void setCfgMap(Map<String, String> cfgMap) {
        this.cfgMap = cfgMap;
    }

    public Map<String, ConfigEntity> getCfgEntityMap() {
        return cfgEntityMap;
    }

    public void setCfgEntityMap(Map<String, ConfigEntity> cfgEntityMap) {
        this.cfgEntityMap = cfgEntityMap;
    }

    @Override
    public String toString() {
        return "ClassConfiguration{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", testList=" + testList +
                ", cfgEntities=" + cfgEntities +
                ", cfgMap=" + cfgMap +
                ", cfgEntityMap=" + cfgEntityMap +
                '}';
    }
}
