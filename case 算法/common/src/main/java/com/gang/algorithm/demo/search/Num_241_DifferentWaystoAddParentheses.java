package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Classname Num_241_DifferentWaystoAddParentheses
 * @Description TODO
 * @Date 2021/8/20
 * @Created by zengzg
 */
public class Num_241_DifferentWaystoAddParentheses extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_241_DifferentWaystoAddParentheses().run();
    }


    @Override
    public void run() {
        diffWaysToCompute("2-1-1");
        logger.info("------> {} <-------", "2-1-1");
    }

    //添加一个 map
    HashMap<String, List<Integer>> map = new HashMap<>();

    public List<Integer> diffWaysToCompute(String input) {
        if (input.length() == 0) {
            return new ArrayList<>();
        }
        //如果已经有当前解了，直接返回
        if (map.containsKey(input)) {
            return map.get(input);
        }
        List<Integer> result = new ArrayList<>();
        int num = 0;
        int index = 0;
        while (index < input.length() && !isOperation(input.charAt(index))) {
            num = num * 10 + input.charAt(index) - '0';
            index++;
        }
        if (index == input.length()) {
            result.add(num);
            //存到 map
            map.put(input, result);
            return result;
        }
        for (int i = 0; i < input.length(); i++) {
            if (isOperation(input.charAt(i))) {
                List<Integer> result1 = diffWaysToCompute(input.substring(0, i));
                //一个不同的递归就是一个括号选择
                List<Integer> result2 = diffWaysToCompute(input.substring(i + 1));
                for (int j = 0; j < result1.size(); j++) {
                    for (int k = 0; k < result2.size(); k++) {
                        char op = input.charAt(i);
                        result.add(caculate(result1.get(j), op, result2.get(k)));
                    }
                }
            }
        }
        //存到 map
        map.put(input, result);
        return result;
    }

    private int caculate(int num1, char c, int num2) {
        switch (c) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
        }
        return -1;
    }

    private boolean isOperation(char c) {
        return c == '+' || c == '-' || c == '*';
    }
}
