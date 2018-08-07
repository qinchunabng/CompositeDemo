package com.qin;

/**
 * Created by DELL on 2018/04/15.
 */
public class Node {

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
