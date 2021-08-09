package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.service.IAlgorithmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname FindDuplicateStrings
 * @Description 给定一个字符串，找到没有重复字符的最长子串，返回它的长度
 * @Date 2021/8/8
 * @Created by https://github.com/wind-liang/leetcode
 */
public class FindDuplicateStrings extends AbstractAlgorithmService {

    private static final String DEMO_1 = "abcdabcabvde";

    @Override
    public void run() {

        int maxNum = 0;

        // 解法四 : 数组解法
        maxNum = lengthOfLongestSubstring(DEMO_1);

        logger.info("------> 输出最大无重复字符串 :[{}] <-------", maxNum);

    }


    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128];
        for (int j = 0, i = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;//（下标 + 1） 代表 i 要移动的下个位置
        }
        return ans;
    }
}
