package com.gamg.easyrest.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/other")
@Component
public class TestOtherAPI {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GET
    @Path("/test")
    public void test() {
        logger.info("------> test in .... <-------");
    }
}
