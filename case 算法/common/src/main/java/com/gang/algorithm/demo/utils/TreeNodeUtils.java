package com.gang.algorithm.demo.utils;

import com.gang.algorithm.demo.to.ListNode;
import com.gang.algorithm.demo.to.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname TreeNodeUtils
 * @Description TODO
 * @Date 2021/8/17
 * @Created by zengzg
 */
public class TreeNodeUtils {


    public static TreeNode buildListNode(Object[] list) {
        if (list == null || list.length == 0) {
            return new TreeNode();
        }

        List<TreeNode> nodes = new ArrayList<>(list.length);
        for (Object obj : list) {
            TreeNode treeNode = new TreeNode();
            if (obj != null) {
                treeNode.setVal(obj);
                nodes.add(treeNode);
            } else {
                nodes.add(null);
            }
        }

        for (int i = 0; i < list.length / 2 - 1; i++) {
            TreeNode node = nodes.get(i);
            if (node != null) {
                node.setLeft(nodes.get(i * 2 + 1));
                node.setRight(nodes.get(i * 2 + 2));
            }
        }
        // 只有当总节点数是奇数时，最后一个父节点才有右子节点
        int lastPNodeIndex = list.length / 2 - 1;
        TreeNode lastPNode = nodes.get(lastPNodeIndex);
        lastPNode.setLeft(nodes.get(lastPNodeIndex * 2 + 1));
        if (list.length % 2 != 0) {
            lastPNode.setRight(nodes.get(lastPNodeIndex * 2 + 2));
        }

        return nodes.get(0);
    }
}
