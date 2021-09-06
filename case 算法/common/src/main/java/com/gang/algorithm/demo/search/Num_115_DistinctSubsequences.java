package com.gang.algorithm.demo.search;

import com.alibaba.fastjson.JSONObject;
import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.HashMap;

/**
 * @Classname Num_115_DistinctSubsequences
 * @Description TODO
 * @Date 2021/8/17
 * @Created by zengzg
 */
public class Num_115_DistinctSubsequences extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_115_DistinctSubsequences().run();
    }


    @Override
    public void run() {
//        logger.info("------> 递归之分治 [{}] <-------", numDistinct("rabbbit", "rabbit"));
//        logger.info("------> 递归之回溯 [{}] <-------", numDistinct2("rabbbit", "rabbit"));
        logger.info("------> 动态规划 [{}] <-------", numDistinct3("rabbbit", "rabbit"));
    }

    /**
     * 递归之分治
     *
     * @param s
     * @param t
     * @return
     */
    public int numDistinct(String s, String t) {
        HashMap<String, Integer> map = new HashMap<>();
        int num = numDistinctHelper(s, 0, t, 0, map);
        logger.info("------> map is :[{}] <-------", JSONObject.toJSONString(map));
        return num;
    }

    private int numDistinctHelper(String s, int s_start, String t, int t_start, HashMap<String, Integer> map) {
        //T 是空串，选法就是 1 种
        if (t_start == t.length()) {
            return 1;
        }
        //S 是空串，选法是 0 种
        if (s_start == s.length()) {
            return 0;
        }
        String key = s_start + "@" + t_start;
        //先判断之前有没有求过这个解
        if (map.containsKey(key)) {
            return map.get(key);
        }
        int count = 0;
        //当前字母相等
        if (s.charAt(s_start) == t.charAt(t_start)) {
            //从 S 选择当前的字母，此时 S 跳过这个字母, T 也跳过一个字母。
            count = numDistinctHelper(s, s_start + 1, t, t_start + 1, map)
                    //S 不选当前的字母，此时 S 跳过这个字母，T 不跳过字母。
                    + numDistinctHelper(s, s_start + 1, t, t_start, map);
            //当前字母不相等
        } else {
            //S 只能不选当前的字母，此时 S 跳过这个字母， T 不跳过字母。
            count = numDistinctHelper(s, s_start + 1, t, t_start, map);
        }
        //将当前解放到 map 中
        map.put(key, count);
        return count;
    }


    int count = 0;

    /**
     * 递归之回溯
     *
     * @param s
     * @param t
     * @return
     */
    public int numDistinct2(String s, String t) {
        HashMap<String, Integer> map = new HashMap<>();
        numDistinctHelper2(s, 0, t, 0, map);

        map.keySet().forEach(item -> {
            logger.info("------>  [{}] : [{}] <-------", item, map.get(item));
        });
        return count;
    }

    private void numDistinctHelper2(String s, int s_start, String t, int t_start,
                                    HashMap<String, Integer> map) {
        if (t_start == t.length()) {
            count++;
            return;
        }
        if (s_start == s.length()) {
            return;
        }
        String key = s_start + "@" + t_start;
        if (map.containsKey(key)) {
            count += map.get(key);
            return;
        }
        int count_pre = count;
        //当前字母相等，s_start 后移一个，t_start 后移一个
        if (s.charAt(s_start) == t.charAt(t_start)) {
            numDistinctHelper(s, s_start + 1, t, t_start + 1, map);
        }
        //出来以后，继续尝试不选择当前字母，s_start 后移一个，t_start 不后移
        numDistinctHelper(s, s_start + 1, t, t_start, map);

        //将增量存起来
        int count_increment = count - count_pre;
        map.put(key, count_increment);
    }


    public int numDistinct3(String s, String t) {
        int s_len = s.length();
        int t_len = t.length();
        int[] dp = new int[s_len + 1];
        for (int i = 0; i <= s_len; i++) {
            dp[i] = 1;
        }
        //倒着进行，T 每次增加一个字母
        for (int t_i = t_len - 1; t_i >= 0; t_i--) {
            int pre = dp[s_len];
            dp[s_len] = 0;
            //倒着进行，S 每次增加一个字母
            for (int s_i = s_len - 1; s_i >= 0; s_i--) {
                int temp = dp[s_i];
                logger.info("------> t - s : {} - {}  <-------", t.charAt(t_i), s.charAt(s_i));
                if (t.charAt(t_i) == s.charAt(s_i)) {
                    dp[s_i] = dp[s_i + 1] + pre;
                } else {
                    dp[s_i] = dp[s_i + 1];
                }
                pre = temp;
            }
        }
        return dp[0];
    }


    public int numDistinct4(String s, String t) {
        int s_len = s.length();
        int t_len = t.length();
        int[] dp = new int[s_len + 1];
        for (int i = 0; i <= s_len; i++) {
            dp[i] = 1;
        }
        for (int t_i = 1; t_i <= t_len; t_i++) {
            int pre = dp[0];
            dp[0] = 0;
            for (int s_i = 1; s_i <= s_len; s_i++) {
                int temp = dp[s_i];
                if (t.charAt(t_i - 1) == s.charAt(s_i - 1)) {
                    dp[s_i] = dp[s_i - 1] + pre;
                } else {
                    dp[s_i] = dp[s_i - 1];
                }
                pre = temp;
            }
        }
        return dp[s_len];
    }
}
