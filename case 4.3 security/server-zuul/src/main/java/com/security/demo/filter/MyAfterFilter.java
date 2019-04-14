package com.security.demo.filter;

import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.netflix.zuul.ZuulFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/14 17:31
 * @Version 1.0
 **/
public class MyAfterFilter extends ZuulFilter {

    Logger logger = LoggerFactory.getLogger(getClass());

    public String filterType() {
        return "post";
    }

    public int filterOrder() {
        return 999;
    }

    public boolean shouldFilter() {
        RequestContext context = getCurrentContext();
        return context.getRequest().getParameter("service") == null;
    }

    public Object run() {
        try {
            RequestContext context = getCurrentContext();
            InputStream stream = context.getResponseDataStream();
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            context.setResponseBody("Modified via setResponseBody(): " + body);
        } catch (IOException e) {
            logger.info("exception :{}", e.getMessage());
        }
        return null;
    }
}
