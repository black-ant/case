package com.gang.cloud.template.demo.controller;

import com.gang.cloud.template.demo.entity.CloudTemplateEntity;
import com.gang.cloud.template.demo.entity.api.ICloudTemplateEntity;
import com.gang.cloud.template.demo.service.BuyProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Classname NacosTemplateController
 * @Description TODO
 * @Created by zengzg
 */
@RestController
@RequestMapping("/template")
@RefreshScope
public class TemplateController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;
    @Autowired
    private BuyProductService httpBin;


    @GetMapping("/getBin")
    public Map getBin() {
        return httpBin.get();
    }

    @GetMapping("/delay/{seconds}")
    public Map delay(@PathVariable int seconds) {
        return circuitBreakerFactory.create("delay").run(httpBin.delaySuppplier(seconds), t -> {
            logger.warn("delay call failed error", t);
            Map<String, String> fallback = new HashMap<>();
            fallback.put("hello", "world");
            return fallback;
        });
    }

    @GetMapping("/get")
    public ICloudTemplateEntity get(@RequestParam("desc") String desc) {
        logger.info("------> [测试 Template Coutroller 是否已经配置成功 : {}] <-------", desc);
        ICloudTemplateEntity cloudTemplateEntity = new CloudTemplateEntity();
        cloudTemplateEntity.setUserid(new Random().nextInt(99999));
        cloudTemplateEntity.setUsername("Template Success");
        cloudTemplateEntity.setDesc(desc);
        return cloudTemplateEntity;
    }

    /**
     * 127.0.0.1:8083/template/feign
     *
     * @return
     */
    @GetMapping("/feign")
    public String get() {
        logger.info("------> [测试 UserServer NacosTemplateController  ] <-------");
        return "success";
    }

}
