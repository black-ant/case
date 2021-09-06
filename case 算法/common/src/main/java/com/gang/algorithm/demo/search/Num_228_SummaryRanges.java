package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Num_228_SummaryRanges
 * @Description TODO
 * @Date 2021/8/20
 * @Created by zengzg
 */
public class Num_228_SummaryRanges extends AbstractAlgorithmService {


    public static void main(String[] args) {
        new Num_228_SummaryRanges().run();
    }

    @Override
    public void run() {
        int[] array = {0, 1, 2, 3, 5, 7};
        logger.info("------> {}  <-------", summaryRanges(array));
    }

    class Range {
        int start;
        int end;

        Range(int s, int e) {
            start = s;
            end = e;
        }
    }

    public List<String> summaryRanges(int[] nums) {

        List<String> resStr = new ArrayList<>();

        if (nums.length == 0) {
            return resStr;
        }

        List<Range> res = new ArrayList<>();
        helper(nums, 0, nums.length - 1, res);

        for (Range r : res) {
            if (r.start == r.end) {
                resStr.add(Integer.toString(r.start));
            } else {
                resStr.add(r.start + "->" + r.end);
            }
        }

        return resStr;
    }


    private void helper(int[] nums, int i, int j, List<Range> res) {
        // 判断间隔是否和下标相同
        if (i == j || nums[j] - nums[i] == j - i) {
            add2res(nums[i], nums[j], res);
            return;
        }

        // 通过二分法快速处理
        int m = (i + j) / 2;
        //一半一半的考虑
        helper(nums, i, m, res);
        helper(nums, m + 1, j, res);
    }

    private void add2res(int a, int b, List<Range> res) {
        //判断新加入的范围和之前最后一个范围是否相连
        if (res.isEmpty() || res.get(res.size() - 1).end + 1 != a) {
            res.add(new Range(a, b));
        } else {
            res.get(res.size() - 1).end = b;
        }
    }
}
