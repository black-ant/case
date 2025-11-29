package com.gang.base.controller;

import com.alibaba.fastjson2.JSON;
import com.gang.base.common.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 * <p>
 * 演示 RESTful API 的各种参数接收方式：
 * <ul>
 *     <li>路径参数 @PathVariable</li>
 *     <li>查询参数 @RequestParam</li>
 *     <li>请求体 @RequestBody</li>
 * </ul>
 * </p>
 *
 * @author zengzg
 * @since 2019/12/17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 测试接口
     * <p>
     * GET /user/get/test
     * </p>
     *
     * @return 测试字符串
     */
    @GetMapping("get/test")
    public String test() {
        logger.info("Test method called");
        return "test";
    }

    /**
     * 路径参数示例
     * <p>
     * GET /user/get/path/{key}
     * </p>
     *
     * @param key 路径参数
     * @return 接收到的参数值
     */
    @GetMapping("get/path/{key}")
    public String pathTest(@PathVariable("key") String key) {
        logger.info("Path variable: {}", key);
        return key;
    }

    /**
     * 查询参数示例
     * <p>
     * GET /user/get/param?key=xxx
     * </p>
     *
     * @param key 查询参数
     * @return 接收到的参数值
     */
    @GetMapping("get/param")
    public String getTest(@RequestParam("key") String key) {
        logger.info("Query parameter: {}", key);
        return key;
    }

    /**
     * POST 请求查询参数示例
     * <p>
     * POST /user/post/test?key=xxx
     * </p>
     *
     * @param key 查询参数
     * @return 接收到的参数值
     */
    @PostMapping("post/test")
    public String postTest(@RequestParam("key") String key) {
        logger.info("POST parameter: {}", key);
        return key;
    }

    /**
     * 创建用户接口
     * <p>
     * POST /user/post/create
     * Content-Type: application/json
     * </p>
     *
     * @param user 用户信息（JSON 请求体）
     * @return 处理后的用户信息
     */
    @PostMapping(value = "post/create", produces = "application/json", consumes = "application/json")
    public User create(@RequestBody User user) {
        logger.info("Creating user: {}", JSON.toJSONString(user));
        user.setAge(19);
        return user;
    }
}
