package com.gang.algorithm.demo.to;

/**
 * @Classname TreeNode
 * @Description TODO
 * @Date 2021/8/12
 * @Created by zengzg
 */
public class TreeNode {

    public Object val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(Integer val) {
        this.val = val;
    }

    public TreeNode(Integer val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public Integer getVal() {
        return (Integer) val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
