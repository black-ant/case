package com.security.demo.filter;

import com.google.common.net.MediaType;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/14 17:29
 * @Version 1.0
 **/
public class MyRouteFilter extends ZuulFilter {

    Logger logger =LoggerFactory.getLogger(getClass());

    @Autowired
    private ProxyRequestHelper helper;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().getRouteHost() != null
                && RequestContext.getCurrentContext().sendZuulResponse();
    }

    @Override
    public Object run() {
        logger.info("进入 路由 filter 逻辑 MyRouteFilter");
        return null;
    }
}
