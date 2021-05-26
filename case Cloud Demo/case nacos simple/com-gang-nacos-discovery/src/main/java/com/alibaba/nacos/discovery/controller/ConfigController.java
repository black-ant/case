package com.alibaba.nacos.discovery.controller;

import com.alibaba.nacos.discovery.service.NacosClientConfigService;
import com.alibaba.nacos.discovery.service.NacosDiscoveryBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ConfigController
 * @Description TODO
 * @Date 2021/5/26
 * @Created by zengzg
 */

@RestController
@RequestMapping("config")
public class ConfigController {

    @Autowired
    private NacosClientConfigService clientConfigService;

    /**
     * 使用其中 ConfigService 需要添加包
     * nacos-config-spring-boot-starter
     */
    @Autowired
    private NacosDiscoveryBeanService beanService;

    /**
     * http://127.0.0.1:8089/config/get?dataId=test&groupId=one
     *
     * @param dataId
     * @param groupId
     * @return
     */
    @GetMapping("get")
    public String get(@RequestParam("dataId") String dataId, @RequestParam("groupId") String groupId) {
        String configA = clientConfigService.get(dataId + "1", groupId + "1");
        beanService.get(dataId + "2", groupId + "2");
        return configA;
    }

    /**
     * http://127.0.0.1:8089/config/create?dataId=test&groupId=one&content=messageSuccess
     *
     * @param dataId
     * @param groupId
     * @param content
     * @return
     */
    @GetMapping("create")
    public String create(@RequestParam("dataId") String dataId, @RequestParam("groupId") String groupId, @RequestParam("content") String content) {
        clientConfigService.createOrUpdate(dataId + "1", groupId + "1", content + "1");
        beanService.createOrUpdate(dataId + "2", groupId + "2", content + "2");
        return "Success";
    }

    /**
     * http://127.0.0.1:8089/config/remove?dataId=test&groupId=one
     *
     * @param dataId
     * @param groupId
     * @return
     */
    @GetMapping("remove")
    public String remove(@RequestParam("dataId") String dataId, @RequestParam("groupId") String groupId) {
        clientConfigService.delete(dataId + "1", groupId + "1");
        beanService.get(dataId + "2", groupId + "2");
        return "Success";
    }

}
