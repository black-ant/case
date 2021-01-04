package com.gamg.easyrest.demo.controller;


import com.gamg.easyrest.demo.entity.RESTHeaders;
import com.gamg.easyrest.demo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Component
@Produces({
        MediaType.APPLICATION_JSON, RESTHeaders.APPLICATION_YAML, MediaType.APPLICATION_XML
})
@Consumes({
        MediaType.APPLICATION_JSON, RESTHeaders.APPLICATION_YAML, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN
})
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GET
    @Path("/show")
    public void showUser() {
        logger.info("user is in");
    }


    @GET
    @Path("/getUser")
    public User getUserMsg() {
        return new User("gang", 1, "show user type");
    }
}
