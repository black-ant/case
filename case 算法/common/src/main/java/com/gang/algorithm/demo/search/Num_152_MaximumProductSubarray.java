package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

/**
 * @Classname Num_152_MaximumProductSubarray
 * @Description TODO
 * @Date 2021/8/16
 * @Created by zengzg
 */
public class Num_152_MaximumProductSubarray extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_152_MaximumProductSubarray().run();
    }

    int[] nums = {2, 3, 2, 4, 5, 6, 3, -4, 8};

    @Override
    public void run() {
//        logger.info("------> [{}] <-------", maxProduct(nums));
        logger.info("------> [{}] <-------", maxProduct1(nums));
    }

    /**
     * 找一个乘积最大得连续子数组
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int dpMax = nums[0];
        int dpMin = nums[0];
        int max = nums[0];
        for (int i = 1; i < n; i++) {
            //更新 dpMin 的时候需要 dpMax 之前的信息，所以先保存起来
            // dpMin * nums[i] : 如果当前是负数 , 此时会得到正数
            // dpMax * nums[i] : 如果当前正数 , 上一个也是正数 , 此处会得到一个数
            // nums[i] :
            int preMax = dpMax;

            // 此处不要解读过多 , 如果没有负数的情况下 , 此处只有一个 , dpMin 是为了解决动态规划情况下的负值情况
            dpMax = Math.max(dpMin * nums[i], Math.max(dpMax * nums[i], nums[i]));
            dpMin = Math.min(dpMin * nums[i], Math.min(preMax * nums[i], nums[i]));
            logger.info("------> dpMax :[{}] -- dpMin :[{}] <-------", dpMax, dpMin);
            max = Math.max(max, dpMax);
        }
        return max;
    }


    public int maxProduct1(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int max = 1;
        int res = nums[0];
        for (int i = 0; i < nums.length; i++) {
            max *= nums[i];
            res = Math.max(res, max);
            if (max == 0) {
                max = 1;
            }

        }
        // 注意 , 此处 max 属于 临时变量 , res 才是记录的变量
        max = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            max *= nums[i];
            res = Math.max(res, max);
            if (max == 0) {
                max = 1;
            }
        }

        return res;
    }


}
