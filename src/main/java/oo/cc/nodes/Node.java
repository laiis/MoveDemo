package oo.cc.nodes;

import oo.cc.steps.Direct;

public class Node {
    private String name;
    private Node next;
    private Node prev;
    private Direct direct;

    public Node(String name) {
        this(name, Direct.LEFT);
    }

    public Node(String name, Direct direct) {
        this.name = name;
        this.direct = direct;
    }

    public String getName() {
        return name;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Direct getDirect() {
        return direct;
    }

    public void setDirect(Direct direct) {
        this.direct = direct;
    }

    public boolean canMove() {
        return true;
    }
}
