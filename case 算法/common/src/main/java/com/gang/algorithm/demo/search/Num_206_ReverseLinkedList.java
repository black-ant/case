package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.ListNode;
import com.gang.algorithm.demo.utils.ListNodeUtils;

import java.util.Arrays;

/**
 * @Classname Num_206_ReverseLinkedList
 * @Description TODO
 * @Date 2021/8/18
 * @Created by zengzg
 */
public class Num_206_ReverseLinkedList extends AbstractAlgorithmService {


    public static void main(String[] args) {
        new Num_206_ReverseLinkedList().run();
    }

    @Override
    public void run() {
        ListNode node = ListNodeUtils.buildListNode(Arrays.asList(5, 4, 3, 2, 1, null));
        ListNode newNode = reverseList(node);
        while (newNode != null) {
            logger.info("------> [{}] <-------", newNode.getVal());
            newNode = newNode.getNext();
        }
    }


    public ListNode reverseList(ListNode head) {
        if (head == null)
            return null;
        ListNode pre = null;
        ListNode next;

        while (head != null) {
            next = head.getNext();
            head.setNext(pre);
            pre = head;
            head = next;
        }
        return pre;
    }
}
