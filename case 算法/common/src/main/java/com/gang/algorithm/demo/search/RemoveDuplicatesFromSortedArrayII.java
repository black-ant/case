package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.service.IAlgorithmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname RemoveDuplicatesFromSortedArrayII
 * @Description 给定一个数组，每个数字只允许出现 2 次，将满足条件的数字全部移到前边，并且返回有多少数字
 * @Date 2021/8/9
 * @Created by https://leetcode.wang/leetCode-80-Remove-Duplicates-from-Sorted-ArrayII.html
 */
public class RemoveDuplicatesFromSortedArrayII extends AbstractAlgorithmService {

    @Override
    public void run() {
        int[] nums = {1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 5, 6};
//        int num = removeDuplicates2(nums);
        int num = removeDuplicates3(nums);
        logger.info("------> 返回当前数字 :[{}] <-------", num);
    }

    /**
     * @param nums
     * @return
     */
    public int removeDuplicates2(int[] nums) {
        int slow = 1;
        int fast = 2;
        int max = 2;
        for (; fast < nums.length; fast++) {
            //不相等的话就添加
            // 快慢指针 , slow 在前 , fast 在后
            // fast 会把符合要求的替换前面重复的
            if (nums[fast] != nums[slow - max + 1]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }
        return slow + 1;
    }


    public int removeDuplicates3(int[] nums) {
        int slow = 1;
        int fast = 2;
        for (; fast < nums.length; fast++) {
            // 区别于上一种 , 理解核心 , 核心是fast 从第二位开始 , slow 从第0位开始
            if (nums[fast] != nums[slow - 1]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }
        return slow + 1;
    }
}
