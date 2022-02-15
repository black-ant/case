package com.gang.study.starter.demo.auto;

import com.gang.study.starter.demo.prop.DefaultProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Classname DefaultAutoConfiguration
 * @Description TODO
 * @Date 2021/10/3
 * @Created by zengzg
 */
@Configuration
@EnableConfigurationProperties({DefaultProperties.class})
@EnableAspectJAutoProxy(
        proxyTargetClass = true,
        exposeProxy = true
)
public class DefaultAutoConfiguration {


}
