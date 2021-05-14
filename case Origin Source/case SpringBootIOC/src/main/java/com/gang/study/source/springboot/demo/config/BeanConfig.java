package com.gang.study.source.springboot.demo.config;

import com.gang.study.source.springboot.demo.common.Common2Service;
import com.gang.study.source.springboot.demo.common.CommonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname BeanConfig
 * @Description TODO
 * @Date 2021/5/11
 * @Created by zengzg
 */
@Configuration
public class BeanConfig {


    @Bean(initMethod = "initMethod")
    public CommonService commonService() {
        return new CommonService();
    }
}
