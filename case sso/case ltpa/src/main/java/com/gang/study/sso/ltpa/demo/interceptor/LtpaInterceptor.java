package com.gang.study.sso.ltpa.demo.interceptor;

import com.gang.study.sso.ltpa.demo.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;
import sun.plugin.util.UserProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @Classname LtpaInterceptor
 * @Description TODO
 * @Date 2020/6/30 15:49
 * @Created by zengzg
 */
public class LtpaInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("------> this is in  LtpaInterceptorAdapter<-------");
        String cookieToken = CookieUtils.getCookieValue(request, "ssoLtpaToken");

        return true;
    }
}
