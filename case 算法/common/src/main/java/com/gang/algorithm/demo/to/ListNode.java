package com.gang.algorithm.demo.to;

/**
 * @Classname ListNode
 * @Description TODO
 * @Date 2021/8/16
 * @Created by zengzg
 */
public class ListNode {
    int val;
    ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "" + val;
    }
}
