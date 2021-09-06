package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.TreeNode;
import com.gang.algorithm.demo.utils.TreeNodeUtils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Classname Num_111_MinimumDepthOfBinaryTree
 * @Description TODO
 * @Date 2021/8/17
 * @Created by zengzg
 */
public class Num_111_MinimumDepthOfBinaryTree extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_111_MinimumDepthOfBinaryTree().run();
    }

    @Override
    public void run() {
        TreeNode root = TreeNodeUtils.buildListNode(new Object[]{3, 9, 20, null, null, 15, 7});
        logger.info("------> [{}] <-------", minDepth(root));
    }


    public int minDepth(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        if (root == null)
            return 0;
        queue.offer(root);
        /**********修改的地方*****************/
        int level = 1;
        /***********************************/
        while (!queue.isEmpty()) {
            int thisSize = queue.size(); // 当前层元素的个数
            for (int i = 0; i < thisSize; i++) {
                TreeNode curNode = queue.poll();
                if (curNode != null) {
                    /**********修改的地方*****************/
                    if (curNode.getLeft() == null && curNode.getRight() == null) {
                        return level;
                    }
                    /***********************************/
                    if (curNode.getLeft() != null) {
                        queue.offer(curNode.getLeft());
                    }
                    if (curNode.getRight() != null) {
                        queue.offer(curNode.getRight());
                    }
                }
            }
            level++;
        }
        return level;
    }
}
