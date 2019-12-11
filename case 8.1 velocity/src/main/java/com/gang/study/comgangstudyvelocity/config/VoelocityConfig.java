package com.gang.study.comgangstudyvelocity.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname Velocaity
 * @Description TODO
 * @Date 2019/12/8 18:42
 * @Created by zengzg
 */
@Configuration
public class VoelocityConfig {

    @Bean
    public VelocityEngine velocityEngine() {
        return new VelocityEngine();
    }
}
