package com.gang.cloud.zookeeper.demo.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @Classname RegistryService
 * @Description TODO
 * @Date 2021/8/2
 * @Created by zengzg
 */
@Component
public class RegistryService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String PREFIX = "/services/instance/";

    @Autowired
    private ZookeeperServiceRegistry serviceRegistry;

    @Autowired
    private ServiceDiscovery<ZookeeperInstance> serviceDiscovery;


    public void registerThings(String serviceName, String address, Integer port) {

        // 注册一个实例
        ZookeeperRegistration registration = ServiceInstanceRegistration.builder()
                .defaultUriSpec()
                .address(address)
                .port(port)
                .name(PREFIX + serviceName)
                .build();
        this.serviceRegistry.register(registration);
    }

    public void searchRegistry(String serviceName) {

        logger.info("------> 查询指定的 Name [{}] <-------", serviceName);
        try {
            Collection<ServiceInstance<ZookeeperInstance>> list = serviceDiscovery.queryForInstances(PREFIX + serviceName);
            list.forEach(item -> {
                logger.info("------> registry [{}] <-------", JSONObject.toJSONString(item));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("------> 查询所有的 Names <-------");
        try {
            Collection<String> list = serviceDiscovery.queryForNames();
            list.forEach(item -> {
                logger.info("------> registry [{}] <-------", JSONObject.toJSONString(item));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteRegistry(String serviceName) {
        try {
            Collection<ServiceInstance<ZookeeperInstance>> list = serviceDiscovery.queryForInstances(PREFIX + serviceName);
            list.forEach(item -> {
                logger.info("------> registry [{}] <-------", JSONObject.toJSONString(item));
                ZookeeperRegistration registration = ServiceInstanceRegistration.builder()
                        .defaultUriSpec()
                        .name(item.getName())
                        .build();
                this.serviceRegistry.deregister(registration);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
