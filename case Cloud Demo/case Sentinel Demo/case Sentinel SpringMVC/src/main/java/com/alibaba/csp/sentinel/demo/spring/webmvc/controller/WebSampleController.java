package com.alibaba.csp.sentinel.demo.spring.webmvc.controller;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import static com.alibaba.csp.sentinel.SphU.*;

/**
 *
 */
@RestController
@RequestMapping("/sample")
public class WebSampleController {

    @Resource
    private SampleService sampleService;

    @GetMapping("/test")
    public void test() throws InterruptedException {

        // S1 : 初始化 Rule 规则
        long currentTime = System.currentTimeMillis();
        initFlowRules();
        for (int i = 0; i < 300000; i++) {
            Entry entry = null;
            try {
                // S2 : 标注需要处理的 Resource 类型，和配置中的一一对应
                entry = entry("HelloWorld");
                System.out.println("OK.业务成功执行");
            } catch (BlockException e1) {
                System.out.println("ERROR.业务被阻塞!");
//                Thread.sleep(300);
            } finally {
                if (entry != null) {
//                    System.out.println("Current executing rule: " + JSONObject.toJSONString( entry.getCurNode()));
                    entry.exit();
                  }
            }
        }
        System.out.println("总耗时:"+(System.currentTimeMillis()-currentTime));
    }

    @GetMapping("/testOther")
    public void testOther() throws InterruptedException {
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < 3000000; i++) {
            System.out.println("OK.业务成功执行");
        }
        System.out.println("总耗时:"+(System.currentTimeMillis()-currentTime));
    }


    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();

        // 定义 Rule 规则，这里基于 QPS ，每秒可以请求三次
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(30);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }


    @GetMapping("/testAnnotation")
    public void testAnnotation() throws InterruptedException {
        // S1 : 初始化 Rule 规则
        initFlowRulesAnnotation();
        while (true) {
            try {
                // S2 : 标注需要处理的 Resource 类型，和配置中的一一对应
                sampleService.test();
            } catch (Exception e1) {
                System.out.println("ERROR.业务被阻塞!");
                Thread.sleep(300);
            }
        }
    }

    private static void initFlowRulesAnnotation() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();

        // 定义 Rule 规则，这里基于 QPS ，每秒可以请求三次
        rule.setResource("annotationSource");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(3);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }


    @GetMapping("/dashboard")
    public void dashboard() throws InterruptedException {
        // S1 : 初始化 Rule 规则
        for (int i = 0; i < 100; i++) {
            try {
                // S2 : 标注需要处理的 Resource 类型，和配置中的一一对应
                sampleService.test();
            } catch (Exception e1) {
                System.out.println("ERROR.业务被阻塞!");
                Thread.sleep(300);
            }
        }
    }



}
