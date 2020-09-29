package com.gang.spring.beanmanager.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @Classname APIFilterChild
 * @Description TODO
 * @Date 2020/9/29 15:23
 * @Created by zengzg
 */
@Component
public class APIFilterChild extends APIFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("------> this is in APIFilterChild <-------");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
