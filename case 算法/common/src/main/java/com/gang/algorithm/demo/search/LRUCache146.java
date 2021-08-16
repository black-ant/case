package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.HashMap;

/**
 * @Classname LRUCache146
 * @Description TODO
 * @Date 2021/8/16
 * @Created by zengzg
 */
public class LRUCache146 extends AbstractAlgorithmService {

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        new LRUCache146().run();
    }


    class MyNode {
        Object key;
        Object value;
        MyNode prev = null;
        MyNode next = null;

        MyNode(Object k, Object v) {
            key = k;
            value = v;
        }
    }

    class DoubleLinkedList {
        private MyNode dummyHead = new MyNode(null, null); // 头节点
        private MyNode tail = dummyHead;

        //添加节点到末尾
        public void add(MyNode myNode) {
            tail.next = myNode;
            myNode.prev = tail;
            tail = myNode;
        }

        //得到头结点
        public MyNode getHead() {
            return dummyHead.next;
        }

        //移除当前节点
        public void removeMyNode(MyNode myNode) {
            myNode.prev.next = myNode.next;
            //判断删除的是否是尾节点
            if (myNode.next != null) {
                myNode.next.prev = myNode.prev;
            } else {
                tail = myNode.prev;
            }
            //全部指向 null
            myNode.prev = null;
            myNode.next = null;
        }

        //移动当前节点到末尾
        public void moveToTail(MyNode myNode) {
            removeMyNode(myNode);
            add(myNode);
        }
    }

    public class LRUCache {
        private int capacity = 0;
        private HashMap<Integer, MyNode> map = new HashMap<>();
        private DoubleLinkedList list = new DoubleLinkedList();

        public LRUCache(int capacity) {
            this.capacity = capacity;
        }

        //get 的同时要把当前节点移动到末尾
        public int get(int key) {
            if (map.containsKey(key)) {
                MyNode myNode = map.get(key);
                list.moveToTail(myNode);
                return (int) myNode.value;
            } else {
                return -1;
            }
        }

        //对于之前存在的节点单独考虑
        public void put(int key, int value) {
            if (map.containsKey(key)) {
                MyNode myNode = map.get(key);
                myNode.value = value;
                list.moveToTail(myNode);
            } else {
                //判断是否存满
                if (map.size() == capacity) {
                    //从 map 和 list 中都删除头结点
                    MyNode head = list.getHead();
                    map.remove((int) head.key);
                    list.removeMyNode(head);
                    //插入当前元素
                    MyNode myNode = new MyNode(key, value);
                    list.add(myNode);
                    map.put(key, myNode);
                } else {
                    MyNode myNode = new MyNode(key, value);
                    list.add(myNode);
                    map.put(key, myNode);
                }
            }
        }
    }
}
