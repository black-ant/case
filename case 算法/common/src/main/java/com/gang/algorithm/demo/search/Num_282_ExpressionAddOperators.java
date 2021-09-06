package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Num_282_ExpressionAddOperators
 * @Description TODO
 * @Date 2021/8/24
 * @Created by zengzg
 */
public class Num_282_ExpressionAddOperators extends AbstractAlgorithmService {

    private static final String DEMO_1 = "abcdabcabvde";

    public static void main(String[] args) {
        new Num_282_ExpressionAddOperators().run();
    }

    @Override
    public void run() {
        addOperators("123", 6);
    }


    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<>();
        addOperatorsHelper(num, target, result, new StringBuilder(), 0, 0, 0);
        return result;
    }

    /**
     *
     * 核心点评 : 解法的难处是乘法的优先级处理 , 解决的核心是上一次的操作数 (pre)
     * - path.setLength(len) 用于回溯上一步
     *
     *
     * @param num
     * @param target
     * @param result
     * @param path
     * @param start
     * @param eval
     * @param pre
     */
    private void addOperatorsHelper(String num, int target, List<String> result, StringBuilder path, int start, long eval, long pre) {
        if (start == num.length()) {
            if (target == eval) {
                result.add(path.toString());
            }
            return;

        }
        for (int i = start; i < num.length(); i++) {
            // 数字不能以 0 开头
            if (num.charAt(start) == '0' && i > start) {
                break;
            }
            long cur = Long.parseLong(num.substring(start, i + 1));
            int len = path.length();
            if (start == 0) {
                addOperatorsHelper(num, target, result, path.append(cur), i + 1, cur, cur);
                path.setLength(len);
            } else {

                // 加当前值
                addOperatorsHelper(num, target, result, path.append("+").append(cur), i + 1, eval + cur, cur);
                path.setLength(len);
                // 减当前值
                addOperatorsHelper(num, target, result, path.append("-").append(cur), i + 1, eval - cur, -cur);
                path.setLength(len);
                // 乘当前值
                addOperatorsHelper(num, target, result, path.append("*").append(cur), i + 1, eval - pre + pre * cur,
                        pre * cur);
                path.setLength(len);
            }
        }
    }
}
