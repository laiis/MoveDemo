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
    private final int NODE_SIZE;

    public TheBestStrategy(List<Node> nodeList) {
        this.nodeList = new ArrayList<>(nodeList);
        NODE_SIZE = this.nodeList.size();
    }

    @Override
    public int exec() {
        Node space = findNode0();

        System.out.println("before start move, we show the list:");
        Step.showList(space);

        System.out.println("---------------- start -------------------");

        Step step;
        boolean isAllMove = false;
        long amount = 0;
        NodeQueue<Node> nodeQueue = new NodeQueue<>();

        // initial
        if (space.getPrev() != null && space.getNext() != null) {
            space.getPrev().setPriority(2);
            if (space.getPrev().getPrev() != null) {
                space.getPrev().getPrev().setPriority(1);
            }
            space.getNext().setPriority(2);
            if (space.getNext().getNext() != null) {
                space.getNext().getNext().setPriority(1);
            }
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
                        isAllMove = Step.isAllMove(currentNode, Step.findIndex(space));
                        Step.showList(space);

                        if (isAllMove) {
                            break;
                        }
                    }
                }
            }

            prepare(nodeQueue, siblingNode0(space));
        } while (!isAllMove);

        System.out.println("total: " + amount + " steps");
        return Long.valueOf(amount).intValue();
    }

    private List<Node> siblingNode0(Node node0) {
        if (node0 instanceof Space) {
            List<Node> tempList = new ArrayList<>();

            Node right = node0.getNext();
            Node left = node0.getPrev();
            do {

                if (left != null) {
                    tempList.add(left);
                }

                if (right != null) {
                    tempList.add(right);
                }

                if (left != null) {
                    left = left.getPrev();
                }

                if (right != null) {
                    right = right.getNext();
                }

            } while (left != null || right != null);

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
                if (node.getNext() != null && node.getNext().getDirect() != node.getDirect() && node.getNext().getNext() instanceof Space) {
                    node.setPriority(4);
                } else if (node.getNext() != null && node.getNext().getDirect() == node.getDirect() && node.getNext().getNext() instanceof Space) {
                    node.setPriority(3);
                } else if (node.getNext() instanceof Space && node.getNext().getNext() != null && node.getNext().getNext().getDirect() == node.getDirect()) {
                    node.setPriority(2);
                } else if (node.getNext() instanceof Space) {
                    node.setPriority(1);
                    int idx = Step.findIndex(node);
                    if (idx < (NODE_SIZE / 2 - 2)) {
                        node.setPriority(5 + idx);
                    }
                } else if (node.getPrev() instanceof Space) {
                    node.setPriority(0);
                    continue;
                } else if (node.getPrev() != null && node.getPrev().getPrev() instanceof Space) {
                    node.setPriority(0);
                    continue;
                }
            } else {
                if (node.getPrev() != null && node.getPrev().getDirect() != node.getDirect() && node.getPrev().getPrev() instanceof Space) {
                    node.setPriority(4);
                } else if (node.getPrev() != null && node.getPrev().getDirect() == node.getDirect() && node.getPrev().getPrev() instanceof Space) {
                    node.setPriority(1);
                } else if (node.getPrev() instanceof Space && node.getPrev().getPrev() != null && node.getPrev().getPrev().getDirect() == node.getDirect()) {
                    node.setPriority(3);
                } else if (node.getPrev() instanceof Space) {
                    node.setPriority(2);
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
                if (temp != null && temp.getPriority() >= 0) {
                    if (temp.getPriority() <= node.getPriority()) {
                        temp = nodeQueue.dequeue();
                        stackList.add(temp);
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
