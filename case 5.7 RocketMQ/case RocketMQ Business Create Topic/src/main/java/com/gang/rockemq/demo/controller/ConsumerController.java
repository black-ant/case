package com.gang.rockemq.demo.controller;

import com.gang.rockemq.demo.factory.ListenerFactory;
import com.gang.rockemq.demo.listener.AutoMessageListener;
import com.gang.rockemq.demo.to.OrderTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ConsumerController
 * @Description TODO
 * @Date 2021/9/8
 * @Created by zengzg
 */
@RequestMapping("consumer")
@RestController
public class ConsumerController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ListenerFactory factory;

    /**
     * @return
     */
    @RequestMapping("/factory")
    public String produce() {

        logger.info("------> 开始创建 Consumer <-------");
        for (int i = 0; i < 10; i++) {
            try {
                String topicName = "ant-topic-" + i;
                String group = "ant-group-" + i;
                logger.info("------> 创建 Consumer {} <-------", topicName);
                factory.createConsumer(topicName, group, new AutoMessageListener(topicName));
            } catch (Exception e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            }
        }
        return "success";
    }
}
