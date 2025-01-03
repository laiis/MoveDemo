package oo.cc.strategies.thebest;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;
import oo.cc.steps.Direct;
import oo.cc.steps.NodeQueue;
import oo.cc.steps.Step;
import oo.cc.steps.StepImpl;
import oo.cc.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

public class TheBestStrategy implements Strategy {
    private final List<Node> nodeList;

    public TheBestStrategy(List<Node> nodeList) {
        this.nodeList = new ArrayList<>(nodeList);
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
        NodeQueue<Node> nodeQueue = new NodeQueue<>();

        // initial
        Node space = findNode0();
        if (space.getPrev() != null && space.getNext() != null) {
            space.getPrev().setPriority(2);
            space.getPrev().getPrev().setPriority(1);
            space.getNext().setPriority(2);
            space.getNext().getNext().setPriority(1);
            nodeQueue.enqueue(space.getPrev());
            nodeQueue.enqueue(space.getNext());
            nodeQueue.enqueue(space.getPrev().getPrev());
            nodeQueue.enqueue(space.getNext().getNext());
        }

        Node currentNode = null;

        do {

            while (!nodeQueue.isEmpty()) {
                currentNode = nodeQueue.dequeue();

                if (currentNode.getPriority() >= 0) {
                    step = new StepImpl(currentNode);

                    boolean isSuccess = step.move();
                    if (isSuccess) {
                        amount++;
                        isAllMove = Step.isAllMove(currentNode, Step.findIndex(node0));
                        Step.showList(node0);

                        if (isAllMove) {
                            break;
                        }
                    }
                }
            }

            prepare(nodeQueue, siblingNode0(node0));
        } while (!isAllMove);

        System.out.println("total: " + amount + " steps");
    }

    private List<Node> siblingNode0(Node node0) {
        if (node0 instanceof Space) {
            List<Node> tempList = new ArrayList<>();
            if (node0.getPrev() != null && node0.getPrev().getPrev() == null) {
                tempList.add(0, node0.getPrev());
            } else if (node0.getPrev() != null) {
                tempList.add(node0.getPrev());
            }

            if (node0.getNext() != null && node0.getNext().getNext() == null) {
                tempList.add(0, node0.getNext());
            } else if (node0.getNext() != null) {
                tempList.add(node0.getNext());
            }

            if (node0.getPrev() != null && node0.getPrev().getPrev() != null) {
                tempList.add(node0.getPrev().getPrev());
            }

            if (node0.getNext() != null && node0.getNext().getNext() != null) {
                tempList.add(node0.getNext().getNext());
            }

            return tempList;
        }

        return List.of();
    }

    private void prepare(NodeQueue<Node> nodeQueue, List<Node> nodeList) {
        for (Node node : nodeList) {
            node.setPriority(0);
        }

        for (Node node : nodeList) {
            if (node.getDirect() == Direct.LEFT) {
                if (node.getNext() instanceof Space) {
                    node.setPriority(3);
                } else if (node.getNext() != null && node.getNext().getDirect() != node.getDirect() && node.getNext().getNext() instanceof Space) {
                    node.setPriority(4);
                } else if (node.getNext() != null && node.getNext().getDirect() == node.getDirect() && node.getNext().getNext() instanceof Space) {
                    node.setPriority(2);
                } else if (node.getNext() instanceof Space && node.getNext().getNext().getDirect() == node.getDirect()) {
                    node.setPriority(1);
                } else if (node.getPrev() instanceof Space) {
                    node.setPriority(0);
                    continue;
                } else if (node.getPrev() != null && node.getPrev().getPrev() instanceof Space) {
                    node.setPriority(0);
                    continue;
                }
            } else {
                if (node.getPrev() instanceof Space) {
                    node.setPriority(3);
                } else if (node.getPrev() != null && node.getPrev().getDirect() != node.getDirect() && node.getPrev().getPrev() instanceof Space) {
                    node.setPriority(4);
                } else if (node.getPrev() != null && node.getPrev().getDirect() == node.getDirect() && node.getPrev().getPrev() instanceof Space) {
                    node.setPriority(2);
                } else if (node.getPrev() instanceof Space && node.getPrev().getPrev().getDirect() == node.getDirect()) {
                    node.setPriority(1);
                } else if (node.getNext() instanceof Space) {
                    node.setPriority(0);
                    continue;
                } else if (node.getNext() != null && node.getNext().getNext() instanceof Space) {
                    node.setPriority(0);
                    continue;
                }
            }

            sortedQueue(node, nodeQueue);
        }
    }

    private void sortedQueue(Node node, NodeQueue<Node> nodeQueue) {
        List<Node> stackList = new ArrayList<>();
        if (nodeQueue.isEmpty()) {
            nodeQueue.enqueue(node);
        } else {
            do {
                Node temp = nodeQueue.peek();
                if (temp != null && temp.getPriority() != 0) {
                    if (temp.getPriority() < node.getPriority()) {
                        temp = nodeQueue.dequeue();
                        stackList.add(0, temp);
                    } else {
                        for (Node n : stackList) {
                            nodeQueue.enqueue(n);
                        }
                    }
                } else {
                    nodeQueue.enqueue(node);

                    for (Node n : stackList) {
                        nodeQueue.enqueue(n);
                    }

                    stackList.clear();
                }
            } while (!stackList.isEmpty());
        }

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
