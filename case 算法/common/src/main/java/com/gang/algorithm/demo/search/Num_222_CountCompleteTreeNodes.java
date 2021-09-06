package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.TreeNode;

/**
 * @Classname Num_222_CountCompleteTreeNodes
 * @Description TODO
 * @Date 2021/8/18
 * @Created by zengzg
 */
public class Num_222_CountCompleteTreeNodes extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_222_CountCompleteTreeNodes().run();
    }


    @Override
    public void run() {
    }


    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return getHeight(root.getLeft()) + 1;
        }
    }

    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int height = getHeight(root);
        int rightHeight = getHeight(root.getRight());
        // 左子树是 perfect binary tree
        if (rightHeight == height - 1) {
            // 左子树高度和右子树高度相等
            // 左子树加右子树加根节点
            //return (1 << rightHeight) - 1  + countNodes(root.right) + 1;
            return (1 << rightHeight) + countNodes(root.getRight());
            // 右子树是 perfect binary tree
        } else {
            // 左子树加右子树加根节点
            //return countNodes(root.left) + (1 << rightHeight) - 1 + 1;
            return countNodes(root.getLeft()) + (1 << rightHeight);
        }
    }

}
