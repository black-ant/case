package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

/**
 * @Classname Num_168_ExcelSheetColumnTitle
 * @Description TODO
 * @Date 2021/8/15
 * @Created by zengzg
 */
public class Num_168_ExcelSheetColumnTitle extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_168_ExcelSheetColumnTitle().run();
    }

    @Override
    public void run() {
        convertToTitle(268);
    }

    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            int c = n % 26;
            if (c == 0) {
                c = 26;
                n -= 1;
            }
            sb.insert(0, (char) ('A' + c - 1));
            n /= 26;
        }
        return sb.toString();
    }
}
