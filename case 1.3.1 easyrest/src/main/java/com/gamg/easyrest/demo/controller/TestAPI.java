package com.gamg.easyrest.demo.controller;

import com.gamg.easyrest.demo.entity.RESTHeaders;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * 接口对于实现类的方式 , 此方式利于Feign 调用 , 但是注意 实现类的注入@Component
 *
 * @Link TestAPIService
 */
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
