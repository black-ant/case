package com.gang.study.comgangstudyvelocity;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@RunWith(JUnit4.class)
public class ComGangStudyVelocityApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VelocityEngine velocityEngine;

    @Test
    public void velocityTest() {
        Map<String, Object> model = new HashMap<String, Object>();
        //        model.put("time", XDateUtils.nowToString());
        model.put("message", "这是测试的内容。。。");
        model.put("toUserName", "张三");
        model.put("fromUserName", "老许");
        //        logger.info("------>  <-------");
        System.out.println(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "welcome.vm", "UTF-8", model));
    }

}
