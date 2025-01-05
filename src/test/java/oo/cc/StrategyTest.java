package oo.cc;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;
import oo.cc.steps.Direct;
import oo.cc.strategies.Strategy;
import oo.cc.strategies.thebest.TheBestStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StrategyTest {

    private static List<Node> initial() {
        Node nodeA = new Node("A", Direct.RIGHT);
        Node nodeB = new Node("B", Direct.RIGHT);
        Node nodeC = new Node("C", Direct.RIGHT);
        Node nodeD = new Node("D", Direct.RIGHT);
        Node node0 = new Space("0", Direct.NONE);
        Node nodea = new Node("a");
        Node nodeb = new Node("b");
        Node nodec = new Node("c");
        Node noded = new Node("d");

        List<Node> nodeList = new ArrayList<>();
        nodeList.add(noded);
        nodeList.add(nodec);
        nodeList.add(nodeb);
        nodeList.add(nodea);
        nodeList.add(nodeA);
        nodeList.add(nodeB);
        nodeList.add(nodeC);
        nodeList.add(nodeD);
        nodeList.add(node0);

        /*
            d c b a x A B C D
        */
//        orderNode(noded, nodec, nodeb, nodea, node0, nodeA, nodeB, nodeC, nodeD);

        return nodeList;
    }

    private List<Node> genNode(int size) {
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

    @Test
    public void testTheBestStrategy() {
        int N = 7;
        List<Node> nodeList = genNode(N);
//        Strategy strategy = new ExhaustiveStrategy(nodeList);
        orderNode(nodeList);
        Strategy strategy = new TheBestStrategy(nodeList);
        int amount = strategy.exec();
        int expected = N * (N + 2);
        Assertions.assertEquals(expected, amount);
    }
}
