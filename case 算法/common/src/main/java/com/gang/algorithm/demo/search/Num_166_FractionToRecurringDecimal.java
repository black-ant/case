package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.HashMap;

/**
 * @Classname Num_166_FractionToRecurringDecimal
 * @Description TODO
 * @Date 2021/8/15
 * @Created by zengzg
 */
public class Num_166_FractionToRecurringDecimal extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_166_FractionToRecurringDecimal().run();
    }


    @Override
    public void run() {
        fractionToDecimal(11, 21);
    }

    public String fractionToDecimal(int numerator, int denominator) {
        long num = numerator;
        long den = denominator;
        String sign = "";
        //确定符号
        if (num > 0 && den < 0 || num < 0 && den > 0) {
            sign = "-";
        }
        //转为正数
        num = Math.abs(num);
        den = Math.abs(den);
        //记录整数部分
        long integer = num / den;
        //计算余数
        num = num - integer * den;
        HashMap<Long, Integer> map = new HashMap<>();
        int index = 0;
        String decimal = "";//记录小数部分
        int repeatIndex = -1;//保存重复的位置
        while (num != 0) {
            num *= 10;//余数乘以 10 作为新的被除数
            if (map.containsKey(num)) {
                repeatIndex = map.get(num);
                break;
            }
            //保存被除数
            map.put(num, index);
            //保存当前的商
            long decimalPlace = num / den;
            //加到所有的商中
            decimal = decimal + decimalPlace;
            //计算新的余数
            num = num - decimalPlace * den;
            index++;
        }
        //是否存在循环小数
        if (repeatIndex != -1) {
            String dec = decimal;
            return sign + integer + "." + dec.substring(0, repeatIndex) + "(" + dec.substring(repeatIndex) + ")";
        } else {
            if (decimal == "") {
                return sign + integer;
            } else {
                return sign + integer + "." + decimal;
            }
        }
    }
}
