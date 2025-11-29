package com.gamg.easyrest.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 测试资源类
 * <p>
 * 演示 JAX-RS 基础用法：
 * <ul>
 *     <li>@Path - 定义资源路径</li>
 *     <li>@GET - HTTP GET 方法</li>
 *     <li>@Produces - 响应内容类型</li>
 * </ul>
 * </p>
 *
 * @author zengzg
 * @since 1.0
 */
@Path("/other")
@Component
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 测试接口
     * <p>
     * GET /other/test
     * </p>
     *
     * @return 测试响应
     */
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        logger.info("Test endpoint called");
        return "RESTEasy test OK";
    }
}
