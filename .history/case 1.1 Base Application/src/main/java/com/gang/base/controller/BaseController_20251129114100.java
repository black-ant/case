package com.gang.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 基础控制器
 * <p>
 * 演示最基本的 Spring MVC 控制器用法。
 * </p>
 *
 * @author zengzg
 * @since 1.0
 */
@RestController
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 回调接口示例
     * <p>
     * POST /callBack
     * </p>
     *
     * @param requestBody 请求体内容
     * @return 处理结果
     */
    @PostMapping("callBack")
    public String callBack(@RequestBody Map<String, Object> requestBody) {
        logger.info("Received callback: {}", requestBody);
        return "callback received";
    }

    /**
     * 健康检查接口
     * <p>
     * GET /test
     * </p>
     *
     * @return 健康状态
     */
    @GetMapping("test")
    public String test() {
        logger.info("Health check OK");
        return "OK";
    }
}
