package oo.cc;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;
import oo.cc.steps.Direct;
import oo.cc.strategies.exhaustive.ExhaustiveStrategy;
import oo.cc.strategies.Strategy;
import oo.cc.strategies.thebest.TheBestStrategy;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        for (int i = 2; i <= 26; i++) {
            List<Node> nodeList = initial(i);
            orderNode(nodeList);
//            Strategy strategy = new ExhaustiveStrategy(nodeList);
            Strategy strategy = new TheBestStrategy(nodeList);
            strategy.exec();
            System.out.println();
        }

    }

    private static List<Node> initial(int size) {
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