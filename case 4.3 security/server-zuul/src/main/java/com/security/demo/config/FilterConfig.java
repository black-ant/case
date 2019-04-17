package com.security.demo.config;

import com.security.demo.filter.MyAfterFilter;
import com.security.demo.filter.MyBeforeFirstFilter;
import com.security.demo.filter.MyBeforeSecondFilter;
import com.security.demo.filter.MyRouteFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/16 12:23
 * @Version 1.0
 **/
@Configuration
public class FilterConfig {

    @Bean
    public MyAfterFilter myAfterFilter() {
        return new MyAfterFilter();
    }

    @Bean
    public MyBeforeFirstFilter myBeforeFirstFilter() {
        return new MyBeforeFirstFilter();
    }

    @Bean
    public MyBeforeSecondFilter myBeforeSecondFilter() {
        return new MyBeforeSecondFilter();
    }

    @Bean
    public MyRouteFilter myRouteFilter() {
        return new MyRouteFilter();
    }
}
