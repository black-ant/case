package com.gang.web.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname ErrorConfig
 * @Description TODO
 * @Date 2021/1/7 17:47
 * @Created by zengzg
 */
//@ControllerAdvice
public class ErrorConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public void dealWithNoHandlerFoundException(NoHandlerFoundException exception, HttpServletResponse response) {
        logger.info("------> this is 404 exception <-------");
//        sendRedirectSSO(response, exception.getClass().getSimpleName());
    }


    public String sendRedirectSSO(HttpServletResponse response, String uid) {

        String redirectUrl = "http://www.baidu.com?error=" + uid;
        try {

            response.sendRedirect(redirectUrl);
            // response.sendRedirect(url + "?errorId=" + uid);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }
        return redirectUrl;
    }
}
