package com.alibaba.nacos.discovery.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.discovery.client.SampleFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("discovery")
public class DiscoveryController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @NacosInjected
    private NamingService namingService;


    @Autowired
    private SampleFeignClient sampleFeignClient;

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }


    @GetMapping(value = "/feign")
    public String feign() throws NacosException {
        logger.info("------> :{} <-------", sampleFeignClient.get(
                "gang.param.test001", "DEFAULT_GROUP"));
        return "success";
    }

    @GetMapping(value = "/user")
    public String user() throws NacosException {
        logger.info("------> user :{} <-------", sampleFeignClient.getUser());
        return "success";
    }
}
