package com.security.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/16 12:14
 * @Version 1.0
 **/
@Component
public class LoginFailureHandle implements AuthenticationFailureHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("is in failue");
        logger.info("exception is {}", exception.getMessage());
    }
}
