package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Num_229_MajorityElementII
 * @Description TODO
 * @Date 2021/8/20
 * @Created by zengzg
 */
public class Num_229_MajorityElementII extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_229_MajorityElementII().run();
    }

    /**
     * 判断字符串模式是不是相同
     */
    @Override
    public void run() {
        int[] arrays = {2, 2, 2, 2, 4, 5, 6, 7, 8, 9};
        logger.info("------> [{}] <-------", majorityElement(arrays));
    }

    public List<Integer> majorityElement(int[] nums) {
        int n = nums.length;

        int group1 = 0;
        int count1 = 0;

        int group2 = 1;
        int count2 = 0;

        // 这里的核心是 抵消不是 1:1 , 而是 1:2
        for (int i = 0; i < n; i++) {
            if (nums[i] == group1) {
                count1++;
            } else if (nums[i] == group2) {
                count2++;
            } else if (count1 == 0) {
                group1 = nums[i];
                count1 = 1;
            } else if (count2 == 0) {
                group2 = nums[i];
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        //计算两个队伍的数量,因为可能只存在一个数字的数量超过了 n/3
        count1 = 0;
        count2 = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == group1) {
                count1++;
            }
            if (nums[i] == group2) {
                count2++;
            }
        }
        //只保存数量大于 n/3 的队伍
        List<Integer> res = new ArrayList<>();
        if (count1 > n / 3) {
            res.add(group1);
        }

        if (count2 > n / 3) {
            res.add(group2);
        }
        return res;
    }
}
