package com.qin;

/**
 * Created by DELL on 2018/04/15.
 */
public class LinkedList {

    private Node head;
    private int length;

    public void add(int value){
        //初始化需要加入的节点
        Node node=new Node(value);
        Node tmp=head;
        //找到尾节点
        while(tmp.next!=null){
            tmp=tmp.next;
        }
        tmp.next=node;
        length++;
    }

    public static class Node {

        //数据域
        private int data;
        //指针域，指向一个节点
        private Node next;

        public Node() {
        }

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
