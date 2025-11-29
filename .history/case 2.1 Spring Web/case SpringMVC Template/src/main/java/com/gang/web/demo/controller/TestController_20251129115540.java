package com.gang.web.demo.controller;

import com.gang.web.demo.to.ModuleTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试控制器
 * <p>
 * 演示 Spring MVC 的各种请求处理方式：
 * <ul>
 *     <li>路径参数 @PathVariable</li>
 *     <li>查询参数 @RequestParam</li>
 *     <li>请求体 @RequestBody</li>
 *     <li>Spring 容器操作</li>
 * </ul>
 * </p>
 *
 * @author zengzg
 * @since 2021/1/7
 */
@RestController
@RequestMapping("test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 简单 GET 请求示例
     * <p>
     * GET /test/get
     * </p>
     *
     * @return 成功标识
     */
    @GetMapping("get")
    public String get() {
        logger.info("Processing GET request");
        // 演示从 Spring 容器获取 Bean
        applicationContext.getBean(SessionController.class);
        return "success";
    }

    /**
     * 带参数的 GET 请求示例
     * <p>
     * GET /test/getParam/{key}?name=xxx
     * </p>
     * <p>
     * 演示同时使用路径参数和查询参数。
     * </p>
     *
     * @param key  路径参数
     * @param name 查询参数
     * @return 成功标识
     */
    @GetMapping("getParam/{key}")
    public String getWithParams(@PathVariable("key") String key, @RequestParam("name") String name) {
        logger.info("Path variable: {}, Query param: {}", key, name);
        return "success";
    }

    /**
     * POST 请求体示例
     * <p>
     * POST /test/getBody
     * Content-Type: application/json
     * </p>
     * <p>
     * 演示接收 JSON 请求体并返回处理后的对象。
     * </p>
     *
     * @param moduleTO 请求体对象
     * @return 处理后的对象
     */
    @PostMapping("getBody")
    public ModuleTO getBody(@RequestBody ModuleTO moduleTO) {
        logger.info("Received module: {}", moduleTO);
        moduleTO.setName(moduleTO.getName() + "_processed");
        return moduleTO;
    }

    /**
     * 云服务接口示例
     * <p>
     * GET /test/cloud
     * </p>
     *
     * @return 云服务信息
     */
    @GetMapping("cloud")
    public String cloud() {
        return "Cloud service is running";
    }
}
