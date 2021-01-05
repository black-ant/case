package com.gang.spring.exceptionhandle.demo.controller;

import com.gang.spring.exceptionhandle.demo.service.TestException;
import com.gang.spring.exceptionhandle.demo.service.TestSevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
@RestController
@Consumes({MediaType.TEXT_PLAIN})
@Produces({MediaType.APPLICATION_JSON})
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TestSevice testSevice;

    @Path("/chandle")
    @GET
    public void conHandle() {
        logger.info("11111  ----  this is in chandle");
        throw new TestException();
    }


    @Path("/shandle")
    @GET
    public void serHandle() {
        logger.info("2222   ----- shandle");
        testSevice.setviceTest();
    }
}
