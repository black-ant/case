package com.gang.bean.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * Bean 的加载控制方式 : 后置加载
     * - @DependsOn
     * - @Bean 时传入对应的方法参数
     * - @AutoConfigureOrder能改变spring.factories中的@Configuration的顺序
     *
     */

    /**
     * Bean 的加载控制方式 : 前置加载
     * - 启动类中构造器注入 : Application(DemoBean demoBean)
     * - 使用 InstantiationAwareBeanPostProcessorAdapter
     * - 使用 META-INF/spring.factories
     *
     *
     */

    // 注意事项 :
    /**
     * - Order 不能控制 Bean 的加载顺序
     */


    // 排除Bean
    /**
     *
     * - Scan 扫描时排除
     * @ComponentScan(excludeFilters = {
     *                        @Filter(type = FilterType.REGEX, pattern = {
     * 				"com.ruoyi.framework.config.ShiroConfig"})
     * 	})
     *
     * - TypeExcludeFilter 自定义排除 + ApplicationContextInitializer + factories
     *
     */


    // Configuration 加载舒徐
    /**
     * - META-INF/spring.factories
     * - @AutoConfigureAfter
     * - @ConditionalOnBean
     * - @AutoConfigureOrder ?
     * - ConfigurationClassPostProcessor
     */

    // https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration
    // https://www.baeldung.com/spring-boot-custom-auto-configuration

}
