package com.gang.cloud.sentinel.demo.controller;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname FlowRuleManagerController
 * @Description TODO
 * @Date 2021/6/23
 * @Created by zengzg
 */
@RestController
@RequestMapping("rule")
public class FlowRuleManagerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("get")
    public void getRule() {
        logger.info("------> this is in get <-------");
        List<FlowRule> rules = FlowRuleManager.getRules();
        rules.forEach(item -> {
            logger.info("------> rule item :[{}] <-------", JSONObject.toJSONString(item));
        });
    }
}
