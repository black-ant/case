package com.gang.study.jersey.demo.controller;

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
@Path("user")
public class TestService {

    @Path("hello")
    @GET
    public String hello() {
        return "success";
    }
}
