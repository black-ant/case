package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.utils.ArraysUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @Classname Num_120_Triangle
 * @Description TODO
 * @Date 2021/8/17
 * @Created by zengzg
 */
public class Num_120_Triangle extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_120_Triangle().run();
    }

    int[][] board = {
            {2},
            {3, 4},
            {6, 5, 7},
            {4, 1, 8, 3}
    };

    @Override
    public void run() {
        List<List<Integer>> list = ArraysUtils.arraysToList(board);
        logger.info("------> [{}] <-------", minimumTotal(list));
    }


    public int minimumTotal(List<List<Integer>> triangle) {
        HashMap<String, Integer> map = new HashMap<>();
        return minimumTotalHelper(0, 0, triangle, map);
    }

    private int minimumTotalHelper(int row, int col, List<List<Integer>> triangle, HashMap<String, Integer> map) {
        if (row == triangle.size()) {
            return 0;
        }
        String key = row + "@" + col;
        if (map.containsKey(key)) {
            return map.get(key);
        }
        int min = Integer.MAX_VALUE;
        List<Integer> cur = triangle.get(row);
        min = Math.min(min, cur.get(col) + minimumTotalHelper(row + 1, col, triangle, map));
        if (col + 1 < cur.size()) {
            min = Math.min(min, cur.get(col + 1) + minimumTotalHelper(row + 1, col + 1, triangle, map));
        }
        map.put(key, min);
        return min;
    }
}
