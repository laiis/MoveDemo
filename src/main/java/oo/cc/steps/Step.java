package oo.cc.steps;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;

public interface Step {
    boolean move();

    boolean isNoWay();

    static void showList(Node somenode) {
        Node start = somenode;
        Node temp;
        while ((temp = start.getPrev()) != null) {
            start = temp;
        }
        temp = start;
        do {
            System.out.print(temp.getName() + " ");
            temp = temp.getNext();
        } while (temp != null);
        System.out.println();
    }

    static void swap(Node right, Node left) {
        if (right == null || left == null) {
            return;
        }

        Node nextNode = right.getNext();
        Node targetParent = left.getPrev();
        right.setPrev(targetParent);
        right.setNext(left);
        left.setPrev(right);
        left.setNext(nextNode);
        if (targetParent != null) {
            targetParent.setNext(right);
        }
        if (nextNode != null) {
            nextNode.setPrev(left);
        }
    }

    static void swapWithGap(Node right, Node left) {
        if (left == null || right == null) {
            return;
        }

        Node prevNode = left.getPrev();
        Node gapNode = left.getNext(); // = right.getPrev();
        Node targetNext = right.getNext();

        left.setNext(targetNext);
        left.setPrev(gapNode);
        right.setNext(gapNode);
        right.setPrev(prevNode);

        if (gapNode != null) {
            gapNode.setPrev(right);
            gapNode.setNext(left);
        }

        if (prevNode != null) {
            prevNode.setNext(right);
        }

        if (targetNext != null) {
            targetNext.setPrev(left);
        }
    }

    static boolean isAllMove(Node somenode, int pivot) {
        Node next = findStart(somenode);
        if (next == null) {
            return false;
        }

        boolean isAllMove = true;
        while (next != null) {
            int idx = findIndex(next);
            if (next.getDirect() == Direct.LEFT) {
                isAllMove = (idx > pivot);
            } else {
                isAllMove = (idx < pivot);
            }

            if (!isAllMove) {
                break;
            }

            next = next.getNext();
            if (next instanceof Space) {
                next = next.getNext();
            }
        }

        return isAllMove;
    }

    static Node findStart(Node somenode) {
        Node start = somenode;
        Node temp;
        while ((temp = start.getPrev()) != null) {
            start = temp;
        }
        temp = start;
        return temp;
    }

    static int findIndex(Node somenode) {
        Node temp = findStart(somenode);

        int index = 0;
        while (temp != null && !temp.getName().equals(somenode.getName())) {
            index++;
            temp = temp.getNext();
        }

        return index;
    }
}
