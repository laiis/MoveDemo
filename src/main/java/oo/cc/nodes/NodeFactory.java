package oo.cc.nodes;

import oo.cc.steps.Direct;
import java.util.ArrayList;
import java.util.List;

public class NodeFactory {

    public static List<Node> createNodeChain(int size) {
        List<Node> nodeList = genNode(size);
        orderNode(nodeList);
        return nodeList;
    }

    private static List<Node> genNode(int size) {
        List<Node> nodeList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Node node = new Node(String.valueOf((char) ('a' + i)), Direct.LEFT);
            nodeList.add(node);
        }

        nodeList.add(new Space("0", Direct.NONE));

        for (int i = 0; i < size; i++) {
            Node node = new Node(String.valueOf((char) ('A' + i)), Direct.RIGHT);
            nodeList.add(node);
        }

        return nodeList;
    }

    private static void orderNode(List<Node> nodeList) {
        Node temp = nodeList.get(0);
        for (int i = 1; i < nodeList.size(); i++) {
            temp.setNext(nodeList.get(i));
            temp = nodeList.get(i);
        }

        temp = nodeList.get(nodeList.size() - 1);
        for (int i = nodeList.size() - 2; i >= 0; i--) {
            temp.setPrev(nodeList.get(i));
            temp = nodeList.get(i);
        }
    }
}
