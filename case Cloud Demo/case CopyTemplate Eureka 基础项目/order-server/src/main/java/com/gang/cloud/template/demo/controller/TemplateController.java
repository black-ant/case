package com.gang.cloud.template.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.demo.client.AccountFeignClient;
import com.gang.cloud.template.demo.client.ProductFeignClient;
import com.gang.cloud.template.demo.entity.CloudTemplateEntity;
import com.gang.cloud.template.demo.entity.api.ICloudTemplateEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private AccountFeignClient accountFeignClient;
    @Autowired
    private ProductFeignClient productFeignClient;

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
     * @return
     */
    @GetMapping("/feign")
    public String get() {
        logger.info("------> [测试 UserServer NacosTemplateController  ] <-------");
        logger.info("------> {} <-------", JSONObject.toJSONString(accountFeignClient.get("order-server")));
//        logger.info("------> {} <-------", JSONObject.toJSONString(productFeignClient.get("order-server")));
        return "success";
    }

}
