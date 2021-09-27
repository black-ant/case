package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

/**
 * @Classname Num_299_BullsAndCows
 * @Description TODO
 * @Date 2021/9/11
 * @Created by zengzg
 */
public class Num_299_BullsAndCows extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_299_BullsAndCows().run();
    }

    @Override
    public void run() {
        getHint("1807", "7810");
    }

    public String getHint(String secret, String guess) {
        int A = 0;
        int[] mapS = new int[10];
        int[] mapG = new int[10];
        for (int i = 0; i < guess.length(); i++) {
            // 数字和位置均相等
            if (secret.charAt(i) == guess.charAt(i)) {
                A++;
            } else {
                mapS[secret.charAt(i) - '0']++;
                mapG[guess.charAt(i) - '0']++;
            }
        }
        int B = 0;
        // 数字相等但是位置不相等
        for (int i = 0; i < 10; i++) {
            B += Math.min(mapS[i], mapG[i]);
        }
        return A + "A" + B + "B";
    }


    public String getHint2(String secret, String guess) {
        int bulls = 0;
        int cows = 0;
        int[] numbers = new int[10];
        for (int i = 0; i < secret.length(); i++) {
            int s = secret.charAt(i) - '0';
            int g = guess.charAt(i) - '0';
            if (s == g) bulls++;
            else {
                //当前数小于 0, 说明之前在 guess 中出现过, 和 secret 当前的数匹配
                if (numbers[s] < 0) cows++;
                //当前数大于 0, 说明之前在 secret 中出现过, 和 guess 当前的数匹配
                if (numbers[g] > 0) cows++;
                //secret 中的数, 计数加 1
                numbers[s]++;
                //guess 中的数, 计数减 1
                numbers[g]--;
            }
        }
        return bulls + "A" + cows + "B";
    }
}
