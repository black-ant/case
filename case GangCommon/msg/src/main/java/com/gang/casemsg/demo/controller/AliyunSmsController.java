package com.gang.casemsg.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.common.msgapi.config.BaseMsgConfig;
import com.gang.common.msgapi.logic.MsgConnect;
import com.gang.common.msgapi.to.MsgBaseBody;
import com.gang.common.sms.AliyunContent;
import com.gang.common.sms.AliyunSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname AliyunSmsController
 * @Description TODO
 * @Date 2020/3/14 17:23
 * @Created by zengzg
 */
@RestController
@RequestMapping("/aliyun")
public class AliyunSmsController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MsgConnect connect;

    @GetMapping("send")
    public String send() {

        AliyunContent aliyunContent = new AliyunContent();
        aliyunContent.setReceiver("");
        aliyunContent.buidlAttr("code", "123456");

        AliyunSetting aliyunSetting = new AliyunSetting();
        aliyunSetting.setAccessSecret("");
        aliyunSetting.setAccessToken("");
        aliyunSetting.setSignName("");
        aliyunSetting.setTemplateCode("");

        connect.build(aliyunSetting).send(aliyunContent);

        return "";
    }
}
