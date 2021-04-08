package com.gang.comgangcasedwr.service;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Classname Demo2Service
 * @Description 前端 Web 调用后端 Server
 * @Date 2021/4/8
 * @Created by zengzg
 */
@Service
@RemoteProxy
public class Demo2Service {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @return
     */
    @RemoteMethod
    public String hello() {
        return "hello";
    }

    /**
     * @param msg
     * @return
     */
    @RemoteMethod
    public String echo(String msg) {
        return msg;
    }

}
