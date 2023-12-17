package com.gang.web.demo.config;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname DefaultHandlerMapping
 * @Description TODO
 * @Date 2022/9/25
 * @Created by zengzg
 */
@Service
public class DefaultHandlerMapping implements HandlerMapping {

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        return null;
    }
}
