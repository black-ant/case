package com.gang.cloud.template.demo.controller;

import com.gang.cloud.template.api.ProductClient;
import com.gang.cloud.template.api.UserClient;
import com.gang.cloud.template.demo.entity.CloudTemplateEntity;
import com.gang.cloud.template.demo.entity.api.ICloudTemplateEntity;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TemplateController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference(lazy = true, check = false)
    private ProductClient productClient;

    @Reference(lazy = true, check = false)
    private UserClient userClient;


    @GetMapping("/get")
    public ICloudTemplateEntity get(@RequestParam("desc") String desc) {
        logger.info("------> [测试 Template Coutroller 是否已经配置成功 : {}] <-------", desc);
        ICloudTemplateEntity cloudTemplateEntity = new CloudTemplateEntity();
        cloudTemplateEntity.setUserid(new Random().nextInt(99999));
        cloudTemplateEntity.setUsername("Template Success");
        cloudTemplateEntity.setDesc(desc);
        return cloudTemplateEntity;
    }


    @GetMapping("/feign")
    public String get() {
        logger.info("------> [测试 Account TemplateController  ] <-------");
//        logger.info("------> {} <-------", JSONObject.toJSONString(productClient.get("order-server")));
//        logger.info("------> {} <-------", JSONObject.toJSONString(userFeignClient.get("order-server")));
        return "success";
    }

}
