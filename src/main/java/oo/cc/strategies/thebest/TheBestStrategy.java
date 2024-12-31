package oo.cc.strategies.thebest;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;
import oo.cc.steps.Step;
import oo.cc.strategies.Strategy;
import oo.cc.strategies.exhaustive.StepImpl;

import java.util.List;

public class TheBestStrategy implements Strategy {
    private final List<Node> nodeList;

    public TheBestStrategy(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    @Override
    public void exec() {
        Node node0 = findNode0();

        System.out.println("before start move, we show the list:");
        Step.showList(node0);

        System.out.println("---------------- start -------------------");

        Step step;
        boolean isAllMove = false;
        long amount = 0;
        do {
            for (Node node : nodeList) {
                step = new StepImpl(node);

                boolean isMove = step.move();
                if (isMove) {
                    Step.showList(node);
                    amount++;

                    isAllMove = Step.isAllMove(node, Step.findIndex(node0));
                    if (isAllMove) {
                        break;
                    }
                }
            }
        } while (!isAllMove);

        System.out.println("total: " + amount + " steps");

    }

    private Node findNode0() {
        for (Node node : nodeList) {
            if (node instanceof Space) {
                return node;
            }
        }

        return null;
    }
}
