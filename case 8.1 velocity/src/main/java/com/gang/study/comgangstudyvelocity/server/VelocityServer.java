package com.gang.study.comgangstudyvelocity.server;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname VelocityServer
 * @Description TODO
 * @Date 2019/12/8 17:48
 * @Created by zengzg
 */
@Component
public class VelocityServer implements ApplicationRunner {

    @Autowired
    private VelocityEngine velocityEngine;

    public void test() {
        Map<String, Object> model = new HashMap<String, Object>();
        //        model.put("time", XDateUtils.nowToString());
        model.put("message", "这是测试的内容。。。");
        model.put("toUserName", "张三");
        model.put("fromUserName", "老许");
        //        logger.info("------>  <-------");
        System.out.println(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "velocity/welcome.vm", "UTF-8", model));

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        test();
    }
}
