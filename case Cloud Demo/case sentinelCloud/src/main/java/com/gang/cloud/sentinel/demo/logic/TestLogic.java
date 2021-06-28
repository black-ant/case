package com.gang.cloud.sentinel.demo.logic;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/10/8 23:20
 * @Created by zengzg
 */
public class TestLogic {

//    public static void main(String[] args) {
//        initFlowRules();
//        while (true) {
//            Entry entry = null;
//            try {
//                entry = SphU.entry("HelloWorld");
//                /*您的业务逻辑 - 开始*/
//                System.out.println("hello world");
//                /*您的业务逻辑 - 结束*/
//            } catch (BlockException e1) {
//                /*流控逻辑处理 - 开始*/
//                System.out.println("block!");
//                /*流控逻辑处理 - 结束*/
//            } finally {
//                if (entry != null) {
//                    entry.exit();
//                }
//            }
//        }
//    }
//
//    private static void initFlowRules() {
//        List<FlowRule> rules = new ArrayList<>();
//        FlowRule rule = new FlowRule();
//        rule.setResource("HelloWorld");
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        // Set limit QPS to 20.
//        rule.setCount(20);
//        rules.add(rule);
//        FlowRuleManager.loadRules(rules);
//    }
}
