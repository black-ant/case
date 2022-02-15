package com.alibaba.nacos.discovery.controller;

import com.alibaba.cloud.nacos.endpoint.NacosDiscoveryEndpoint;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.discovery.service.NacosClientConfigService;
import com.alibaba.nacos.discovery.service.NacosClientNodesService;
import com.alibaba.nacos.discovery.service.NacosDiscoveryBeanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("discovery")
public class UserDiscoveryController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @NacosInjected
    private NamingService namingService;

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
        logger.info("------> this is in get  <-------");
        return namingService.getAllInstances(serviceName);
    }

    @GetMapping("/user")
    public String getUser() {
        logger.info("------> this is in user <-------");
        return "Discovery User";
    }


    @Autowired
    private NacosClientNodesService nodesService;

    /**
     * http://127.0.0.1:8089/discovery/getClient?serviceName=clientServiceName
     *
     * @param serviceName
     * @return
     */
    @GetMapping("getClient")
    public List<Instance> getClient(@RequestParam("serviceName") String serviceName) {
        List<Instance> instanceList = nodesService.get(serviceName);
//        namingService.get(dataId + "2", groupId + "2");
        return instanceList;
    }

    /**
     * http://127.0.0.1:8089/discovery/create?serviceName=clientServiceName&ip=127.0.0.1&port=8086
     *
     * @param serviceName
     * @param ip
     * @param port
     * @return
     */
    @GetMapping("create")
    public String create(@RequestParam("serviceName") String serviceName, @RequestParam("ip") String ip, @RequestParam("port") Integer port) {
        nodesService.createOrUpdate(serviceName, ip, port);
//        namingService.createOrUpdate(dataId + "2", groupId + "2", content + "2");
        return "Success";
    }

    /**
     * http://127.0.0.1:8089/discovery/remove?serviceName=clientServiceName&ip=127.0.0.1&port=8086
     *
     * @param serviceName
     * @param ip
     * @return
     */
    @GetMapping("remove")
    public String remove(@RequestParam("serviceName") String serviceName, @RequestParam("ip") String ip, @RequestParam("port") Integer port) {
        nodesService.delete(serviceName, ip, port);
//        namingService.get(dataId + "2", groupId + "2");
        return "Success";
    }
}
