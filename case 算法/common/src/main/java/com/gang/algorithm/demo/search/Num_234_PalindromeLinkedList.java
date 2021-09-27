package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.ListNode;
import com.gang.algorithm.demo.utils.ListNodeUtils;

import java.util.Arrays;

/**
 * @Classname Num_234_PalindromeLinkedList
 * @Description TODO
 * @Date 2021/9/11
 * @Created by zengzg
 */
public class Num_234_PalindromeLinkedList extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_279_PerfectSquares().run();
    }

    @Override
    public void run() {
        isPalindrome(ListNodeUtils.buildListNode(Arrays.asList(1, 2, 2, 1)));
    }


    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 找中点，链表分成两个
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 第二个链表倒置
        ListNode newHead = reverseList(slow);

        // 前一半和后一半依次比较
        while (newHead != null) {
            if (head.val != newHead.val) {
                return false;
            }
            head = head.next;
            newHead = newHead.next;
        }
        return true;
    }

    private ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode tail = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = tail;
            tail = head;
            head = temp;
        }

        return tail;
    }
}
