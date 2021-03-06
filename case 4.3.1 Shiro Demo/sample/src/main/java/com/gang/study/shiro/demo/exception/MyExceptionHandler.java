package com.gang.study.shiro.demo.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname MyExceptionHandler
 * @Description TODO
 * @Date 2021/3/6 22:42
 * @Created by zengzg
 */
@ControllerAdvice
public class MyExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public String ErrorHandler(AuthorizationException e) {
        logger.error("没有通过权限验证！", e);
        return "没有通过权限验证！";
    }
}
