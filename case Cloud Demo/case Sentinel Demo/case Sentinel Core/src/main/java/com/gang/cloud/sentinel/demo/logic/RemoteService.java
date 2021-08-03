package com.gang.cloud.sentinel.demo.logic;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname RemoteService
 * @Description TODO
 * @Date 2021/8/3
 * @Created by zengzg
 */
@Component
public class RemoteService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String get() {
        return "success";
    }

    /**
     * 发起限流操作 , 可以通过压测工具或者传入 QPS 参数
     *
     * @param qpsNum
     * @return
     */
    public String flowControl(Integer qpsNum) {
        logger.info("------> [初始化 Sentinel Rule] <-------");
        logger.info("------> [Step 1 : 发起业务流程 , 通过 Sentinel API ] <-------");

        for (int i = 0; i < qpsNum; i++) {
            logger.info("------> [进入 Flow Control 业务逻辑 :{}] <-------", i);
        }
        return "success";
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        InitExecutor.doInit();
    }
}
