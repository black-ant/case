package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.Arrays;

/**
 * @Classname Num_198_HouseRobber
 * @Description TODO
 * @Date 2021/8/15
 * @Created by zengzg
 */
public class Num_198_HouseRobber extends AbstractAlgorithmService {

    int[] nums = {1, 2, 3, 3, 6, 8, 1};

    public static void main(String[] args) {
        new Num_198_HouseRobber().run();
    }


    @Override
    public void run() {
//        logger.info("------> 最终结果 :{} <-------", rob(nums));
        logger.info("------> 动态规划最终结果 :{} <-------", rob2(nums));
    }

    public int rob(int[] nums) {
        int[] map = new int[nums.length + 1];
        Arrays.fill(map, -1);
//        return robHelpler(nums, nums.length, map);
        return robHelpler(nums, nums.length);
    }

    private int robHelpler(int[] nums, int n, int[] map) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return nums[0];
        }
        if (map[n] != -1) {
            return map[n];
        }
        // PS : Max 是为了比较大小值
        // PS : 注意 , 递归的核心在于它不是只有一级
        // PS "
        int a = robHelpler(nums, n - 2, map) + nums[n - 1];
        int b = robHelpler(nums, n - 1, map);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("------> a :[{}] , b:[{}] ,n[{}]<-------", a, b, n);
        int res = Math.max(a, b);
        map[n] = res;
        return res;
    }

    private int robHelpler(int[] nums, int n) {

        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return nums[0];
        }

        int a = robHelpler(nums, n - 1);
        int b = robHelpler(nums, n - 2) + nums[n - 1];
        logger.info("------> a :[{}] , b:[{}] ,n[{}]<-------", a, b, n);
        int res = Math.max(a, b);
        return res;
    }


    public int rob2(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return nums[0];
        }

        int pre = 0; //代替上边代码中的 dp[i - 2]
        int cur = nums[0]; //代替上边代码中的 dp[i - 1] 和 dp[i]
        for (int i = 2; i <= n; i++) {
            int temp = cur;
            logger.info("------> temp -> {}<-------", temp);
            cur = Math.max(pre + nums[i - 1], cur);
            logger.info("------> cur -> {} + add -> {}<-------", cur, nums[i - 1]);
            pre = temp;
            logger.info("------> pre -> {}<-------", pre);
        }
        return cur;
    }


}
