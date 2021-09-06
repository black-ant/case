package com.gang.algorithm.demo.to;

/**
 * @Classname ListNode
 * @Description TODO
 * @Date 2021/8/16
 * @Created by zengzg
 */
public class ListNode {

    Integer val;
    ListNode next;

    public ListNode(Integer x) {
        val = x;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "" + val;
    }
}
