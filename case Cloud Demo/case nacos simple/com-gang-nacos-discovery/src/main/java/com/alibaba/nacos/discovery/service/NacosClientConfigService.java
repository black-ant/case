package com.alibaba.nacos.discovery.service;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.discovery.listener.ConfigListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @Classname NacosClientService
 * @Description TODO
 * @Date 2021/5/26
 * @Created by zengzg
 */
@Component
public class NacosClientConfigService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ConfigService configService;

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        configService = NacosFactory.createConfigService(properties);
    }


    /**
     * 获取 Nacos Config
     *
     * @param dataId
     * @param groupId
     * @return
     */
    public String get(String dataId, String groupId) {
        String content = "";
        try {
            content = configService.getConfig(dataId, groupId, 5000);
            logger.info("------> 获取 Config GroupID [{}] -- DataID [{}] Success ,The value :[{}] <-------", dataId, groupId, content);

            configService.addListener(dataId, groupId, new ConfigListener());

        } catch (NacosException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            e.printStackTrace();
        }

        return content;


    }

    /**
     * 创建 Nacos Config
     *
     * @param dataId
     * @param groupId
     * @param content
     */
    public void createOrUpdate(String dataId, String groupId, String content) {
        try {
            logger.info("------> 创建 Config GroupID [{}] -- DataID [{}] Success ,The value :[{}] <-------", dataId, groupId, content);
            configService.publishConfig(dataId, groupId, content);
        } catch (NacosException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 移除 Nacos Config
     *
     * @param dataId
     * @param groupId
     */
    public void delete(String dataId, String groupId) {
        try {
            configService.removeConfig(dataId, groupId);
            logger.info("------> 删除 Config GroupID [{}] -- DataID [{}] Success  <-------", dataId, groupId);

            configService.removeListener(dataId, groupId, null);

        } catch (NacosException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            e.printStackTrace();
        }
    }


}
