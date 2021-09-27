package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.HashMap;

/**
 * @Classname Num_279_PerfectSquares
 * @Description TODO
 * @Date 2021/9/11
 * @Created by zengzg
 */
public class Num_279_PerfectSquares extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_279_PerfectSquares().run();
    }

    @Override
    public void run() {

    }


    public int numSquares(int n) {
        return numSquaresHelper(n, new HashMap<Integer, Integer>());
    }

    private int numSquaresHelper(int n, HashMap<Integer, Integer> map) {
        if (map.containsKey(n)) {
            return map.get(n);
        }
        if (n == 0) {
            return 0;
        }
        int count = Integer.MAX_VALUE;
        for (int i = 1; i * i <= n; i++) {
            count = Math.min(count, numSquaresHelper(n - i * i, map) + 1);
        }
        map.put(n, count);
        return count;
    }
}
