package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.ListNode;
import com.gang.algorithm.demo.utils.ListNodeUtils;

import java.util.Arrays;

/**
 * @Classname Num_160_IntersectionOfTwoLinkedLists
 * @Description TODO
 * @Date 2021/8/16
 * @Created by zengzg
 */
public class Num_160_IntersectionOfTwoLinkedLists extends AbstractAlgorithmService {


    public static void main(String[] args) {
        new Num_160_IntersectionOfTwoLinkedLists().run();
    }


    @Override
    public void run() {
        // 这里有点可以理解为齿轮或者错位换 , 时针总会被分针赶上
        ListNode node1 = ListNodeUtils.buildListNode(Arrays.asList(1, 2, 4, 8, 3, 5, 6, 7, 2, 6));
        ListNode node2 = ListNodeUtils.buildListNode(Arrays.asList(9, 6, 7, 2, 6));

        getIntersectionNode(node1, node2);
    }


    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;

        ListNode a = headA;
        ListNode b = headB;

        while (a != b) {
            logger.info("Before ------> a is [{}],b is [{}] <-------", a, b);
            a = a == null ? headB : a.getNext();
            b = b == null ? headA : b.getNext();
            logger.info("After ------> a is [{}],b is [{}] <-------", a, b);
        }

        return a;
    }
}
