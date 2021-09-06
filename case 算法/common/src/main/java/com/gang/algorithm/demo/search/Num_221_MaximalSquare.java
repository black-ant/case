package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

/**
 * @Classname Num_221_MaximalSquare
 * @Description TODO
 * @Date 2021/8/18
 * @Created by zengzg
 */
public class Num_221_MaximalSquare extends AbstractAlgorithmService {


    char[][] grid = {
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
    };

    public static void main(String[] args) {
        new Num_202_HappyNumber().run();
    }

    /**
     * 平方和最后等于1 就是快乐
     */
    @Override
    public void run() {
        logger.info("------> [{}] <-------", maximalSquare(grid));
    }


    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        // 保存以当前数字结尾的连续 1 的个数
        int[][] width = new int[matrix.length][matrix[0].length];
        // 记录最大边长
        int maxSide = 0;
        // 遍历每一行
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                // 更新 width
                if (matrix[row][col] == '1') {
                    if (col == 0) {
                        width[row][col] = 1;
                    } else {
                        width[row][col] = width[row][col - 1] + 1;
                    }
                } else {
                    width[row][col] = 0;
                }
                // 当前点作为正方形的右下角进行扩展
                int curWidth = width[row][col];
                // 向上扩展行
                for (int up_row = row; up_row >= 0; up_row--) {
                    int height = row - up_row + 1;
                    if (width[up_row][col] <= maxSide || height > curWidth) {
                        break;
                    }
                    maxSide = Math.max(height, maxSide);
                }
            }
        }
        return maxSide * maxSide;
    }

    public int maximalSquare1(char[][] matrix) {
        int rows = matrix.length;
        if (rows == 0) {
            return 0;
        }
        int cols = matrix[0].length;
        int[] dp = new int[cols + 1];
        int maxSide = 0;
        int pre = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                int temp = dp[j];
                if (matrix[i - 1][j - 1] == '0') {
                    dp[j] = 0;
                } else {
                    dp[j] = Math.min(dp[j - 1], Math.min(dp[j], pre)) + 1;
                    maxSide = Math.max(dp[j], maxSide);
                }
                pre = temp;
            }
        }
        return maxSide * maxSide;
    }
}
