package com.gamg.easyrest.demo.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/test")
@Produces({
        MediaType.APPLICATION_JSON, RESTHeaders.APPLICATION_YAML, MediaType.APPLICATION_XML
})
@Consumes({
        MediaType.APPLICATION_JSON, RESTHeaders.APPLICATION_YAML, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN
})
public interface TestAPI {

    @GET
    @Path("/show")
    public void showMsg();



}
