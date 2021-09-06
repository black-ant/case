package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Classname Num_227_BasicCalculatorII
 * @Description TODO
 * @Date 2021/8/20
 * @Created by zengzg
 */
public class Num_227_BasicCalculatorII extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_227_BasicCalculatorII().run();
    }

    @Override
    public void run() {
        logger.info("------> 中缀转后缀后再次处理 :{} <-------", calculate("3+2*2"));
    }

    public int calculate(String s) {
        String[] polish = getPolish(s); // 转后缀表达式
        return evalRPN(polish);
    }

    // 中缀表达式转后缀表达式
    private String[] getPolish(String s) {
        List<String> res = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        char[] array = s.toCharArray();
        int n = array.length;
        int temp = -1; // 累加数字，-1 表示当前没有数字
        for (int i = 0; i < n; i++) {
            if (array[i] == ' ') {
                continue;
            }
            // 遇到数字
            if (isNumber(array[i])) {
                // 进行数字的累加
                if (temp == -1) {
                    temp = array[i] - '0';
                } else {
                    temp = temp * 10 + array[i] - '0';
                }
            } else {
                // 遇到其它操作符，将数字加入到结果中
                if (temp != -1) {
                    res.add(temp + "");
                    temp = -1;
                }
                // 遇到操作符将栈中的操作符加入到结果中
                while (!stack.isEmpty()) {
                    // 栈顶优先级更低就结束
                    if (compare(array[i] + "", stack.peek())) {
                        break;
                    }
                    res.add(stack.pop());
                }
                // 当前操作符入栈
                stack.push(array[i] + "");

            }
        }
        // 如果有数字，将数字加入到结果
        if (temp != -1) {
            res.add(temp + "");
        }
        // 栈中的其他元素加入到结果
        while (!stack.isEmpty()) {
            res.add(stack.pop());
        }
        String[] sArray = new String[res.size()];
        // List 转为 数组
        for (int i = 0; i < res.size(); i++) {
            sArray[i] = res.get(i);
        }
        return sArray;
    }

    private boolean compare(String op1, String op2) {
        if (op1.equals("*") || op1.equals("/")) {
            return op2.equals("+") || op2.equals("-");
        }
        return false;
    }

    public int evalRPN(String[] tokens) {
        Stack<String> stack = new Stack<>();
        for (String t : tokens) {
            if (isOperation(t)) {
                int a = stringToNumber(stack.pop());
                int b = stringToNumber(stack.pop());
                int ans = eval(b, a, t.charAt(0));
                stack.push(ans + "");
            } else {
                stack.push(t);
            }
        }
        return stringToNumber(stack.pop());
    }

    private int eval(int a, int b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
        }
        return 0;
    }

    private int stringToNumber(String s) {
        int sign = 1;
        int start = 0;
        if (s.charAt(0) == '-') {
            sign = -1;
            start = 1;
        }
        int res = 0;
        for (int i = start; i < s.length(); i++) {
            res = res * 10 + s.charAt(i) - '0';
        }
        return res * sign;
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isOperation(String t) {
        return t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/");
    }

}
