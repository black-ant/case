package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

/**
 * @Classname Num_67_AddBinary
 * @Description TODO
 * @Date 2021/8/9
 * @Created by zengzg
 */
public class Num_67_AddBinary extends AbstractAlgorithmService {

    @Override
    public void run() {
        logger.info("------> 二进制求值 :{} <-------", addBinary("1010", "1011"));
    }


    public String addBinary(String a, String b) {
        StringBuilder ans = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        while (i >= 0 || j >= 0) {
            int num1 = i >= 0 ? a.charAt(i) - 48 : 0;
            int num2 = j >= 0 ? b.charAt(j) - 48 : 0;
            int sum = num1 + num2 + carry;
            carry = 0;
            if (sum >= 2) {
                sum = sum % 2;
                carry = 1;
            }
            ans.append(sum);
            i--;
            j--;

        }

        if (carry == 1) {
            ans.append(1);
        }
        return ans.reverse().toString();
    }
}
