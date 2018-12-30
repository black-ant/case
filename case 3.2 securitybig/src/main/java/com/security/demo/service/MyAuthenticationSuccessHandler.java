package com.security.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.Utils.CommonUtils;
import com.security.demo.entity.UserLog;
import com.security.demo.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserLogService logService;
    @Autowired
    private UserService userService;


    // Authentication  封装认证信息
    // 登录方式不同，Authentication不同
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        logger.info("MyAuthenticationSuccessHandler login success!");
        response.setContentType("application/json;charset=UTF-8");         // 把authentication对象转成 json 格式 字符串 通过 response 以application/json;charset=UTF-8 格式写到响应里面去
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
        logger.info((String)request.getSession().getAttribute("username"));
//        Users user  =  userService.findUserByName((String)request.getSession().getAttribute("username"));
//        CommonUtils.reflect(user);
//        logService.save(new UserLog(String.valueOf(user.getId()),user.getUsername(),"名称为" +user.getUsername() + "的用户登录成功，登录IP为" + request.getRemoteAddr(),new Date()));
        // 父类的方法 就是 跳转
        super.onAuthenticationSuccess(request, response, authentication);

    }
}
