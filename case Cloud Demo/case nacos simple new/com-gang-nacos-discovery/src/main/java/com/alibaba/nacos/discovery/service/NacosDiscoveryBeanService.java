package com.alibaba.nacos.discovery.service;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname NacosDiscoveryBeanService
 * @Description Discovery 中可以使用的对象
 * @Date 2021/5/26
 * @Created by zengzg
 */
@Component
public class NacosDiscoveryBeanService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @NacosInjected
    private ConfigService configService;

    public String get(String dataId, String groupId) {

        logger.info("------> 获取 Config GroupID [{}] -- DataID [{}] Success <-------", dataId, groupId);

        String content = "";
        try {
            content = configService.getConfig(dataId, groupId, 5000);

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
        } catch (NacosException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            e.printStackTrace();
        }
    }


}
