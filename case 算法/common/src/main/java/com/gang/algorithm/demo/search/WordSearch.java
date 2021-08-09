package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import org.springframework.stereotype.Component;

/**
 * @Classname WordSearch 79
 * @Description https://leetcode.wang/leetCode-79-Word-Search.html
 * @Date 2021/8/9
 * @Created by zengzg
 */
public class WordSearch extends AbstractAlgorithmService {

    /**
     * 意思就是从某个字符出发，然后它可以向左向右向上向下移动，走过的路径构成一个字符串，判断是否能走出给定字符串的 word ，还有一个条件就是走过的字符不能够走第二次。
     * <p>
     * 比如 SEE，从第二行最后一列的 S 出发，向下移动，再向左移动，就走出了 SEE。
     * <p>
     * ABCB，从第一行第一列的 A 出发，向右移动，再向右移动，到达 C 以后，不能向左移动回到 B ，并且也没有其他的路径走出 ABCB 所以返回 false。
     */
    public void run() {
        char[][] board = {
                {'a', 'b', 'c', 'c'},
                {'g', 'f', 't', 'e'},
                {'p', 's', 'c', 'u'}
        };

        String word = "tcs";

        Boolean isSuccess = exist(board, word);

        logger.info("------> [是否存在 {}] <-------", isSuccess);
    }


    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        if (rows == 0) {
            return false;
        }
        int cols = board[0].length;
        boolean[][] visited = new boolean[rows][cols];
        //从不同位置开始
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //从当前位置开始符合就返回 true
                if (existRecursive(board, i, j, word, 0, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean existRecursive(char[][] board, int row, int col, String word, int index, boolean[][] visited) {
        //判断是否越界
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return false;
        }
        //当前元素访问过或者和当前 word 不匹配返回 false
        if (visited[row][col] || board[row][col] != word.charAt(index)) {
            return false;
        }
        //已经匹配到了最后一个字母，返回 true
        if (index == word.length() - 1) {
            return true;
        }
        //将当前位置标记位已访问
        visited[row][col] = true;
        //对四个位置分别进行尝试
        boolean up = existRecursive(board, row - 1, col, word, index + 1, visited);
        if (up) {
            return true;
        }
        boolean down = existRecursive(board, row + 1, col, word, index + 1, visited);
        if (down) {
            return true;
        }
        boolean left = existRecursive(board, row, col - 1, word, index + 1, visited);
        if (left) {
            return true;
        }
        boolean right = existRecursive(board, row, col + 1, word, index + 1, visited);
        if (right) {
            return true;
        }
        //当前位置没有选进来，恢复标记为 false
        visited[row][col] = false;
        return false;
    }
}
