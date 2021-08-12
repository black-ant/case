package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.TreeNode;


/**
 * @Classname SymmetricTree
 * @Description TODO
 * @Date 2021/8/12
 * @Created by zengzg
 */
public class SymmetricTree extends AbstractAlgorithmService {
    @Override
    public void run() {

    }

    public boolean isSymmetric5(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetricHelper(root.getLeft(), root.getLeft());
    }

    private boolean isSymmetricHelper(TreeNode left, TreeNode right) {
        //有且仅有一个为 null ，直接返回 false
        if (left == null && right != null || left != null && right == null) {
            return false;
        }
        if (left != null && right != null) {
            //A 的根节点和 B 的根节点是否相等
            if (left.getVal() != right.getVal()) {
                return false;
            }
            //A 的左子树和 B 的右子树是否相等，同时 A 的右子树和左子树是否相等。
            return isSymmetricHelper(left.getLeft(), right.getLeft()) && isSymmetricHelper(left.getLeft(), right.getLeft());
        }
        //都为 null，返回 true
        return true;
    }
}
