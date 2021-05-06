package com.gang.web.demo.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname CookieController
 * @Description TODO
 * @Date 2021/4/27
 * @Created by zengzg
 */
@RestController
@RequestMapping("/cookie")
public class CookieController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/set")
    public void setCookie(HttpServletRequest request, HttpServletResponse responsec) {
        logger.info("------> [为请求设置 Cookie] <-------");
        setCookie("Test001", "1", 2592000, "/esc-sso", "127.0.0.1", request, responsec);
        setCookie("Test002", "2", 2592000, "/", "localhost", request, responsec);
        setCookie("Test003", "3", 2592000, "/esc-sso", "localhost", request, responsec);
    }

    @GetMapping("/get")
    public void getCookie(HttpServletRequest request, HttpServletResponse responsec) {
        getCookie(request);
    }


    public static void setCookie(String key, String value, long age, String path, String domainName,
                                 HttpServletRequest request, HttpServletResponse response) {

        if (StringUtils.isEmpty(key)) {
            return;
        }
        if (StringUtils.isEmpty(path)) {
            path = request.getContextPath();
        }
        if (StringUtils.isEmpty(domainName)) {
            domainName = request.getServerName();
        }
        ResponseCookie cookie = ResponseCookie.from(key, value).httpOnly(false)
                .secure(true)
                .domain(domainName)
                .path(path)
                .maxAge(age).build();

        // 设置Cookie Header
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }


    public void getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                logger.info("------> [当前 Cookie 值 {} ] <-------", JSONObject.toJSONString(cookie));
            }
        }
    }
}
