package com.gang.algorithm.demo.search;

import com.alibaba.fastjson.JSONObject;
import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.TreeNode;
import com.gang.algorithm.demo.utils.TreeNodeUtils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Classname Num_97_InterleavingString
 * @Description TODO
 * @Date 2021/9/2
 * @Created by zengzg
 */
public class Num_97_InterleavingString extends AbstractAlgorithmService {


    public static void main(String[] args) {
        new Num_97_InterleavingString().run();
    }

    // 由 a b 为横轴和纵轴组成的网格 , 是否可以走出 c 的形状
    @Override
    public void run() {
        String a = "aabccd";
        String b = "dbbca";
        String c = "adabbcbccda";

        logger.info("------> [{}] <-------", new Solution().isInterleave(a, b, c));
    }


    class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    class Solution {

        public boolean isInterleave(String s1, String s2, String s3) {
            if (s1.length() + s2.length() != s3.length()) {
                return false;
            }
            Queue<Point> queue = new LinkedList<Point>();
            queue.add(new Point(0, 0));
            //判断是否已经遍历过
            boolean[][] visited = new boolean[s1.length() + 1][s2.length() + 1];
            while (!queue.isEmpty()) {
                Point cur = queue.poll();
                //到达右下角就返回 true
                if (cur.x == s1.length() && cur.y == s2.length()) {
                    return true;
                }
                // 尝试是否能向右走
                int right = cur.x + 1;
                if (right <= s1.length() && s1.charAt(right - 1) == s3.charAt(right + cur.y - 1)) {
                    if (!visited[right][cur.y]) {
                        visited[right][cur.y] = true;
                        queue.offer(new Point(right, cur.y));
                    }
                }

                // 尝试是否能向下走
                int down = cur.y + 1;
                if (down <= s2.length() && s2.charAt(down - 1) == s3.charAt(down + cur.x - 1)) {
                    if (!visited[cur.x][down]) {
                        visited[cur.x][down] = true;
                        queue.offer(new Point(cur.x, down));
                    }
                }

            }
            return false;
        }
    }
}
