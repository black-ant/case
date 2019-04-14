package com.security.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/14 17:27
 * @Version 1.0
 **/
public class MyBeforeFirstFilter extends ZuulFilter {

    Logger logger =LoggerFactory.getLogger(getClass());

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        logger.info("进入filter 逻辑 MyBeforeFirstFilter");
        return null;
    }
}
