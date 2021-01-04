package com.gang.study.jerseyspringboot.demo.service;

import com.gang.study.jerseyspringboot.demo.logic.TestLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2020/4/15 23:25
 * @Created by zengzg
 */
@Component
@Path("test")
public class TestService {

    @Autowired
    private TestLogic testLogic;

    @Path("hello")
    @GET
    public String hello() {
        this.testLogic.test();
        return "success";
    }
}
