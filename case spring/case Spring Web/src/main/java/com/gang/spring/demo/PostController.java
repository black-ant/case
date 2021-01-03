package com.gang.spring.demo;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname PostController
 * @Description TODO
 * @Date 2020/12/30 22:47
 * @Created by zengzg
 */
@RestController
@RequestMapping("/test")
public class PostController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("post")
    public String post(@RequestBody User user) {
        logger.info("------> this is post :{} <-------", JSONObject.toJSONString(user));
        return "success";
    }
}
