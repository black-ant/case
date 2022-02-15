package com.alibaba.nacos.discovery.service;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Cluster;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.Service;
import com.alibaba.nacos.api.naming.pojo.healthcheck.AbstractHealthChecker;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Classname NacosClientService
 * @Description TODO
 * @Date 2021/5/26
 * @Created by zengzg
 */
@Component
public class NacosClientNodesService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private NamingService namingService;

    //    @Value("${spring.cloud.nacos.config.server-addr:127.0.0.1:8848}")
    private String serverAddr = "127.0.0.1:8848";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        namingService = NacosFactory.createNamingService(properties);
    }

    /**
     * 获取 Nacos Config
     * 参数格式
     * {
     * "instanceId": "192.168.0.97#9083#DEFAULT#DEFAULT_GROUP@@nacos-user-server",
     * "ip": "192.168.0.97",
     * "port": 9083,
     * "weight": 1,
     * "healthy": true,
     * "enabled": true,
     * "ephemeral": true,
     * "clusterName": "DEFAULT",
     * "serviceName": "DEFAULT_GROUP@@nacos-user-server",
     * "metadata": {
     * "preserved.register.source": "SPRING_CLOUD"
     * },
     * "ipDeleteTimeout": 30000,
     * "instanceHeartBeatInterval": 5000,
     * "instanceHeartBeatTimeOut": 15000
     * }
     *
     * @param serviceName
     * @return
     */
    public List<Instance> get(String serviceName) {
        List<Instance> content = new LinkedList<Instance>();
        try {
            content = namingService.getAllInstances(serviceName);
            logger.info("------> 获取 Config serviceName [{}]  <-------", serviceName);
        } catch (NacosException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            e.printStackTrace();
        }

        return content;


    }

    /**
     * 创建 Nacos Config
     *
     * @param serviceName
     * @param ip
     * @param port
     */
    public void createOrUpdate(String serviceName, String ip, Integer port) {
        try {
            logger.info("------> 创建 Config GroupID [{}] -- DataID [{}] Success ,The value :[{}] <-------", serviceName, ip, port);
            namingService.registerInstance(serviceName, ip, port, "TEST1");

//            Instance instance = new Instance();
//            instance.setIp("55.55.55.55");
//            instance.setPort(9999);
//            instance.setHealthy(false);
//            instance.setWeight(2.0);
//            Map<String, String> instanceMeta = new HashMap<>();
//            instanceMeta.put("site", "et2");
//            instance.setMetadata(instanceMeta);
//
//            Service service = new Service("nacos.test.4");
//            service.setAppName("nacos-naming");
//
//            service.setProtectThreshold(0.8F);
//            service.setGroupName("CNCF");
//            Map<String, String> serviceMeta = new HashMap<>();
//            serviceMeta.put("symmetricCall", "true");
//            service.setMetadata(serviceMeta);
//            instance.setService(service);
//
//            Cluster cluster = new Cluster();
//            cluster.setName("TEST5");
//            AbstractHealthChecker.Http healthChecker = new AbstractHealthChecker.Http();
//            healthChecker.setExpectedResponseCode(400);
//            healthChecker.setCurlHost("USer-Agent|Nacos");
//            healthChecker.setCurlPath("/xxx.html");
//            cluster.setHealthChecker(healthChecker);
//            Map<String, String> clusterMeta = new HashMap<>();
//            clusterMeta.put("xxx", "yyyy");
//            cluster.setMetadata(clusterMeta);
//
//            instance.setCluster(cluster);
//
//            namingService.registerInstance("nacos.test.4", instance);

        } catch (NacosException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 移除 Nacos Config
     *
     * @param serviceName
     * @param ip
     */
    public void delete(String serviceName, String ip, Integer port) {
        try {
            namingService.deregisterInstance(serviceName, ip, port, "DEFAULT");
            logger.info("------> 删除 Config GroupID [{}] -- DataID [{}] Success  <-------", serviceName, ip);
        } catch (NacosException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            e.printStackTrace();
        }
    }
}
