package com.gang.nacos.config.controller;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.gang.nacos.config.client.SampleFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname FeignController
 * @Description TODO
 * @Date 2021/5/26
 * @Created by zengzg
 */
@RestController
@RequestMapping("feign")
public class FeignController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SampleFeignClient feignClient;

    @GetMapping("get")
    public List<Instance> get() {
        logger.info("------> this is in get <-------");
        return feignClient.get("nacos-user-server");
    }
}
