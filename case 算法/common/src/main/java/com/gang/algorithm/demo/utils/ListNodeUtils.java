package com.gang.algorithm.demo.utils;

import com.gang.algorithm.demo.to.ListNode;

import java.util.List;

/**
 * @Classname ListNodeUtils
 * @Description TODO
 * @Date 2021/8/16
 * @Created by zengzg
 */
public class ListNodeUtils {


    public static ListNode buildListNode(List<Integer> list) {
        ListNode pre = null;
        ListNode root = null;
        for (Integer item : list) {
            ListNode temp = new ListNode(item);
            if (root == null) {
                root = temp;
            } else {
                pre.setNext(temp);
            }
            pre = temp;
        }
        return root;
    }
}
