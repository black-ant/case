package com.gang.study.cloud.eureka.client.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2021/8/12
 * @Created by zengzg
 */
@RequestMapping("/start")
@RestController
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/get")
    public String get() {

        logger.info("------> Step 1 : 获取所有的实例 <-------");
        eurekaClient.getAllKnownRegions().forEach(item -> {
            logger.info("------> region :{} <-------", item);
        });

        logger.info("------> status :{} <-------", eurekaClient.getInstanceRemoteStatus());


        return "success";
    }

    @GetMapping("getServicesList")
    @ResponseBody
    public Object getServicesList() {
        List<List<ServiceInstance>> servicesList = new ArrayList<>();
        //获取服务名称
        List<String> serviceNames = discoveryClient.getServices();
        for (String serviceName : serviceNames) {
            //获取服务中的实例列表
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
            servicesList.add(serviceInstances);
        }
        return servicesList;
    }
}
