package com.alibaba.nacos.discovery.listener;

import com.alibaba.nacos.api.config.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

/**
 * @Classname ConfigListener
 * @Description TODO
 * @Date 2021/5/26
 * @Created by zengzg
 */
public class UserConfigListener implements Listener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Executor getExecutor() {
        logger.info("------> this is in  Executor<-------");
        return null;
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        logger.info("------> this is in  configInfo [{}]<-------", configInfo);
    }
}
