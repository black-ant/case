package com.gang.study.cloud.nacos.config;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname SampleConfigApplication
 * @Description 获取 Config 信息 * @Date 2020/11/3 22:56
 * @Created by zengzg
 */
@SpringBootApplication
@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class SampleConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleConfigApplication.class, args);
    }
}
