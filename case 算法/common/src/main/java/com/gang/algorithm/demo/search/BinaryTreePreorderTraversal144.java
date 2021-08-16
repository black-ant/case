package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.ListNode;
import com.gang.algorithm.demo.to.TreeNode;
import com.gang.algorithm.demo.utils.ListNodeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname BinaryTreePreorderTraversal144
 * @Description TODO
 * @Date 2021/8/16
 * @Created by zengzg
 */
public class BinaryTreePreorderTraversal144 extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new BinaryTreePreorderTraversal144().run();
    }


    @Override
    public void run() {

    }


    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        TreeNode cur = root;
        while (cur != null) {
            //情况 1
            if (cur.left == null) {
                list.add(cur.val);
                cur = cur.right;
            } else {
                //找左子树最右边的节点
                TreeNode pre = cur.left;
                while (pre.right != null && pre.right != cur) {
                    pre = pre.right;
                }
                //情况 2.1
                if (pre.right == null) {
                    list.add(cur.val);
                    pre.right = cur;
                    cur = cur.left;
                }
                //情况 2.2
                if (pre.right == cur) {
                    pre.right = null; //这里可以恢复为 null
                    cur = cur.right;
                }
            }
        }
        return list;
    }
}
