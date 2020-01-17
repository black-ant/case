package com.security.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/14 17:27
 * @Version 1.0
 **/
public class MyBeforeFirstFilter extends ZuulFilter {

    Logger logger = LoggerFactory.getLogger(getClass());

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
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response =requestContext.getResponse();
        HttpServletRequest request = requestContext.getRequest();

        logger.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

        Object accessToken = request.getHeader("Authorization");
        if (accessToken == null) {
            logger.warn("Authorization token is empty");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            requestContext.setResponseBody("无 授权码 ");
            requestContext.setResponseBody("Authorization token is empty");
//            try {
//                logger.warn("进行跳转 : /uaa/login/browser");
//                response.sendRedirect("/uaa/login/browser");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return null;
        }
        logger.info("Authorization token is ok");
        return null;
    }
}
