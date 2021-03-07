package com.gang.study.shiro.demo.exception;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.shiro.demo.common.ResponseTO;
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
public class CommonExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * MVC 统一异常处理 , 非 Shiro 核心内容
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public String ErrorHandler(AuthorizationException exception) {
        logger.info("------> Shiro 认证出现异常 , 抛出  AuthorizationException <-------");
        ResponseTO responseTO = new ResponseTO();
        responseTO.setCode("500");
        responseTO.setMsg("认证异常 : " + exception.getMessage());
        return JSONObject.toJSONString(responseTO);
    }
}
