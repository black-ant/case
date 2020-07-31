package com.gang.study.myfilter.demo.filter;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@WebFilter(filterName = "myFilter", urlPatterns = "/*")
public class MyFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        logger.info("------> this is dofilter <-------");
        logger.info("------> this is getServerName :{} <-------", JSONObject.toJSONString(request.getServerName()));
        logger.info("------> this is getAttribute :{} <-------", JSONObject.toJSONString(request.getAttributeNames()));
        logger.info("------> this is getContentLength :{} <-------", JSONObject.toJSONString(request.getContentLength()));
        logger.info("------> this is getDispatcherType :{} <-------", JSONObject.toJSONString(request.getDispatcherType()));
        logger.info("------> this is getContentType :{} <-------", JSONObject.toJSONString(request.getContentType()));
        logger.info("------> this is getLocalName :{} <-------", JSONObject.toJSONString(request.getLocalName()));
        logger.info("------> this is getLocalAddr :{} <-------", JSONObject.toJSONString(request.getLocalAddr()));
        logger.info("------> this is response :{} <-------", JSONObject.toJSONString(response));
    }

    @Override
    public void destroy() {

    }
}
