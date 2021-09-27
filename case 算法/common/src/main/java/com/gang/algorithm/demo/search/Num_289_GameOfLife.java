package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname Num_289_GameOfLife
 * @Description TODO
 * @Date 2021/9/11
 * @Created by zengzg
 */
public class Num_289_GameOfLife extends AbstractAlgorithmService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        new Num_289_GameOfLife().run();
    }

    int[][] board = {
            {0, 1, 0},
            {0, 0, 1},
            {1, 1, 1},
            {0, 0, 0}
    };

    @Override
    public void run() {
        gameOfLife(board);
    }


    public void gameOfLife(int[][] board) {
        int rows = board.length;
        if (rows == 0) {
            return;
        }
        int cols = board[0].length;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // 右
                check(r, c, r, c + 1, rows, cols, board);
                // 右下
                check(r, c, r + 1, c + 1, rows, cols, board);
                // 下
                check(r, c, r + 1, c, rows, cols, board);
                // 左下
                check(r, c, r + 1, c - 1, rows, cols, board);
                //5, 6, 7 代表下一状态是 1
                if (board[r][c] >= 5 && board[r][c] <= 7) {
                    board[r][c] = 1;
                } else {
                    board[r][c] = 0;
                }

            }
        }
    }

    private void check(int rCur, int cCur, int rNext, int cNext, int rows, int cols, int[][] board) {
        if (rNext < 0 || cNext < 0 || rNext >= rows || cNext >= cols) {
            return;
        }
        //如果是奇数说明之前是 1, 更新它之后的邻居
        if (board[rCur][cCur] % 2 == 1) {
            board[rNext][cNext] += 2;
        }
        //如果是奇数说明之前是 1, 更新当前的位置值
        if (board[rNext][cNext] % 2 == 1) {
            board[rCur][cCur] += 2;
        }
    }

}
