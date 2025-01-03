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

        List<Node> nodeList = initial();
//        Strategy strategy = new ExhaustiveStrategy(nodeList);
        Strategy strategy = new TheBestStrategy(nodeList);
        strategy.exec();
    }

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
//        nodeList.add(nodeA);
//        nodeList.add(nodeB);
//        nodeList.add(nodeC);
//        nodeList.add(nodeD);
//        nodeList.add(node0);
//        nodeList.add(nodea);
//        nodeList.add(nodeb);
//        nodeList.add(nodec);
//        nodeList.add(noded);

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


        noded.setNext(nodec);
        nodec.setNext(nodeb);
        nodeb.setNext(nodea);
        nodea.setNext(node0);
        node0.setNext(nodeA);
        nodeA.setNext(nodeB);
        nodeB.setNext(nodeC);
        nodeC.setNext(nodeD);

        nodeD.setPrev(nodeC);
        nodeC.setPrev(nodeB);
        nodeB.setPrev(nodeA);
        nodeA.setPrev(node0);
        node0.setPrev(nodea);
        nodea.setPrev(nodeb);
        nodeb.setPrev(nodec);
        nodec.setPrev(noded);

        return nodeList;
    }

}