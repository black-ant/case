package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.DemoApplication;
import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import org.springframework.boot.SpringApplication;

/**
 * @Classname NumberOfIslands200
 * @Description TODO
 * @Date 2021/8/15
 * @Created by zengzg
 */
public class NumberOfIslands200 extends AbstractAlgorithmService {

    char[][] grid = {
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
    };

    @Override
    public void run() {
        numIslands(grid);
    }

    public static void main(String[] args) {
        new NumberOfIslands200().run();
    }


    class UnionFind {
        int[] parents;
        int nums;

        public UnionFind(char[][] grid, int rows, int cols) {
            nums = 0;
            // 记录 1 的个数
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == '1') {
                        nums++;
                    }
                }
            }

            //每一个类初始化为它本身
            int totalNodes = rows * cols;
            parents = new int[totalNodes];
            for (int i = 0; i < totalNodes; i++) {
                parents[i] = i;
            }
        }

        void union(int node1, int node2) {
            int root1 = find(node1);
            int root2 = find(node2);
            //发生合并，nums--
            if (root1 != root2) {
                parents[root2] = root1;
                nums--;
            }
        }

        int find(int node) {
            while (parents[node] != node) {
                parents[node] = parents[parents[node]];
                node = parents[node];
            }
            return node;
        }

        int getNums() {
            return nums;
        }
    }

    int rows;
    int cols;

    public int numIslands(char[][] grid) {
        if (grid.length == 0)
            return 0;

        rows = grid.length;
        cols = grid[0].length;
        UnionFind uf = new UnionFind(grid, rows, cols);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == '1') {
                    // 将下边右边的 1 节点和当前节点合并
                    if (row != (rows - 1) && grid[row + 1][col] == '1') {
                        uf.union(node(row, col), node(row + 1, col));
                    }
                    if (col != (cols - 1) && grid[row][col + 1] == '1') {
                        uf.union(node(row, col), node(row, col + 1));
                    }
                }
            }
        }
        return uf.getNums();

    }

    int node(int i, int j) {
        return i * cols + j;
    }
}
