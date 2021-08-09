package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

/**
 * @Classname Search2DMatrix
 * @Description https://leetcode.wang/leetCode-74-Search-a-2D-Matrix.html
 * @Date 2021/8/9
 * @Created by zengzg
 */
public class Search2DMatrix extends AbstractAlgorithmService {

    @Override
    public void run() {

        int[][] board = {
                {14, 16, 19, 22},
                {25, 26, 32, 37},
                {46, 54, 77, 99}
        };

        int num = 77;
        searchMatrix2(board, num);

        logger.info("------> 返回当前数字 :[{}] <-------", num);
    }

    /**
     * 判断一个矩阵中是否存在某个数，矩阵是有序的。
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        if (rows == 0) {
            return false;
        }
        int cols = matrix[0].length;
        int left = 0;
        int right = rows * cols - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int temp = matrix[mid / cols][mid % cols];
            if (temp == target) {
                return true;
            } else if (temp < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }


    public boolean searchMatrix2(int[][] matrix, int target) {

        if (matrix.length == 0) {
            return false;
        }

        int width = matrix[0].length;
        int left = 0;
        int right = matrix.length * width - 1;

        while (left <= right) {
            int middle = (right + left) / 2;
            int temp = matrix[middle / width][middle % width];
            if (temp == target) {
                return true;
            } else if (temp < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return false;
    }


}
