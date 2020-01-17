package com.gang.study.jersey.comgangjersey.controller;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/1/17 14:30
 * @Created by zengzg
 */
@Component
@Path("/start")
public class StartController {

    @Path("/city")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        return "this is ok";
    }

}
