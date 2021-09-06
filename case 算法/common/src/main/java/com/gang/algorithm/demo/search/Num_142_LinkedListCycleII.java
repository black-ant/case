package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.ListNode;

/**
 * @Classname Num_142_LinkedListCycleII
 * @Description TODO
 * @Date 2021/8/24
 * @Created by zengzg
 */
public class Num_142_LinkedListCycleII extends AbstractAlgorithmService {

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        new Num_146_LRUCache().run();
    }


    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        ListNode meet = null;
        while (fast != null) {
            if (fast.getNext() == null) {
                return null;
            }
            slow = slow.getNext();
            fast = fast.getNext().getNext();
            //到达相遇点
            if (fast == slow) {
                meet = fast;
                while (head != meet) {
                    head = head.getNext();
                    meet = meet.getNext();
                }
                return head;
            }
        }
        return null;
    }
}
