package com.gang.study.swaggerv3.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2019/11/24 18:21
 * @Created by zengzg
 */
@Path("/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GET
    @Path("one")
    @ResponseBody
    public String test() {
        logger.info("------> this is in one <-------");
        return "this is ok";
    }
}
