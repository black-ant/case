package com.gang.cloud.sentinel.demo.logic;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/6/22
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 此处是手动代码中添加流量策略 , 可以通过可视化界面完成
        logger.info("------> [Step First : 手动构建 Flow Rule Config , 配置限流策略] <-------");
        buildRule("FlowControl");
        buildRule("FlowControlByAnnotation");
        buildRule("FlowControlSync");
    }

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
            Entry entry = null;
            try {
                // 资源名可使用任意有业务语义的字符串
                entry = SphU.entry("FlowControl");
                logger.info("------> [进入 Flow Control 业务逻辑 :{}] <-------", i);
            } catch (BlockException e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }

        return "success";
    }

    /**
     * 通过注解控制
     *
     * @param qpsNum
     * @return
     */
    @SentinelResource(value = "FlowControlByAnnotation")
    public String flowControlByAnnotation(Integer qpsNum) {
        logger.info("------> [初始化 Sentinel Rule] <-------");
        logger.info("------> [Step 1 : 发起业务流程 , 通过 Sentinel API ] <-------");

        for (int i = 0; i < qpsNum; i++) {
            Entry entry = null;
            try {
                // 资源名可使用任意有业务语义的字符串
                logger.info("------> [进入 Flow Control 业务逻辑 :{}] <-------", i);
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }

        return "success";
    }


    public String flowControlSync(Integer qpsNum) {
        logger.info("------> [初始化 Sentinel Rule] <-------");
        logger.info("------> [Step 1 : 发起业务流程 , 通过 Sentinel API ] <-------");

        for (int i = 0; i < qpsNum; i++) {
            Entry entry = null;

            try {
                // 资源名可使用任意有业务语义的字符串
                SphU.asyncEntry("FlowControlSync");
                logger.info("------> [进入 Flow Control 业务逻辑 :{}] <-------", i);
            } catch (BlockException e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }

        return "success";
    }

    public void buildRule(String resourceName) {

        List<FlowRule> rules = new ArrayList<>();
        // 准备流量规则对象
        FlowRule rule = new FlowRule();

        // 设置 Resource 的 ID -> SphU.entry("HelloWorld")
        rule.setResource(resourceName);

        // 通过 QPS 限流 已经限流数量
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);

        // 添加以及加载 Rule
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
