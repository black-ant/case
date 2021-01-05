package com.gang.casemsg.demo.controller;

import com.gang.common.msgapi.config.BaseMsgConfig;
import com.gang.common.msgapi.logic.MsgConnect;
import com.gang.common.msgapi.to.MsgBaseBody;
import com.gang.common.msgapi.type.MsgType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/3/1 11:20
 * @Created by zengzg
 */
@RestController
@RequestMapping("/msgtest")
public class MsgTestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MsgConnect connect;

    @GetMapping("send")
    public String send() {
        //        RabbitConsumerService rabbitConsumerService = new RabbitConsumerService();
        //        rabbitConsumerService.setConsumer(item -> {
        //            logger.info("this is out :{}", item);
        //        });

        MsgBaseBody body = new MsgBaseBody();
        body.setData("test send");
        //        BaseMsgConfig config = new RabbitConfig();
        //        config.setMsgType(MsgType.RABITMQ);
        //        config.setRouting("myQueue");
        //        connect = connect.init(config);

        //        connect.send(body);


        return "";
    }

}
