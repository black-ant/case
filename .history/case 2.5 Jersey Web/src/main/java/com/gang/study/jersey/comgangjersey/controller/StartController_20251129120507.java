package com.gang.study.jersey.comgangjersey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Jersey 资源类示例
 * <p>
 * 演示 JAX-RS 注解的基本用法：
 * <ul>
 *     <li>@Path - 定义资源路径</li>
 *     <li>@GET/@POST - HTTP 方法</li>
 *     <li>@Produces/@Consumes - 媒体类型</li>
 *     <li>@PathParam - 路径参数</li>
 * </ul>
 * </p>
 *
 * @author zengzg
 * @since 2020/1/17
 */
@Component
@Path("/start")
public class StartController {

    private static final Logger logger = LoggerFactory.getLogger(StartController.class);

    /**
     * 获取城市信息
     * <p>
     * GET /start/city
     * </p>
     *
     * @return JSON 格式的响应
     */
    @GET
    @Path("/city")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getCity() {
        logger.info("Getting city info");
        
        Map<String, Object> result = new HashMap<>();
        result.put("city", "Beijing");
        result.put("country", "China");
        result.put("population", 21540000);
        return result;
    }

    /**
     * 根据 ID 获取信息
     * <p>
     * GET /start/info/{id}
     * </p>
     *
     * @param id 资源 ID
     * @return 响应信息
     */
    @GET
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo(@PathParam("id") String id) {
        logger.info("Getting info for id: {}", id);
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("message", "Success");
        
        return Response.ok(result).build();
    }

    /**
     * 健康检查接口
     * <p>
     * GET /start/health
     * </p>
     *
     * @return 健康状态
     */
    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "Jersey is running!";
    }
}
