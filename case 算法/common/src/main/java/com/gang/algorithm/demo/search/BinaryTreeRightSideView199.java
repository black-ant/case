package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Classname BinaryTreeRightSideView199
 * @Description TODO
 * @Date 2021/8/15
 * @Created by zengzg
 */
public class BinaryTreeRightSideView199 extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new NumberOfIslands200().run();
    }

    @Override
    public void run() {
        rightSideView(null);
    }


    public List<Integer> rightSideView(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        List<Integer> res = new LinkedList<>();
        if (root == null)
            return res;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelNum = queue.size(); // 当前层元素的个数
            for (int i = 0; i < levelNum; i++) {
                TreeNode curNode = queue.poll();
                //只保存当前层的最后一个元素
                if (i == levelNum - 1) {
                    res.add(curNode.getVal());
                }
                if (curNode.getLeft() != null) {
                    queue.offer(curNode.getLeft());
                }
                if (curNode.getRight() != null) {
                    queue.offer(curNode.getRight());
                }

            }
        }
        return res;
    }
}
