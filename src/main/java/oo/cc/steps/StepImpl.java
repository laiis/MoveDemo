package oo.cc.steps;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;

public class StepImpl implements Step {

    private final Node node;
    private int way;
    private final int moveFourDirection = 4;

    public StepImpl(Node node) {
        this.node = node;
    }

    @Override
    public boolean isNoWay() {
        return way >= moveFourDirection;
    }

    @Override
    public void reset() {
        way = 0;
    }

    /*
            d c b a x A B C D
         */
    @Override
    public boolean move() {
        if (!node.canMove() || isNoWay()) {
            return false;
        }

        boolean isMove = false;
        /*
            ExhaustiveStrategy move
         */
        for (int i = 0; i < moveFourDirection; i++) {
            if (i == 0) {
                isMove = moveToNext();
            } else if (i == 1) {
                isMove = moveToNextNext();
            } else if (i == 2) {
                isMove = moveToPrev();
            } else {
                isMove = moveToPrevPrev();
            }

            if (isMove) {
                return true;
            }
        }

        return isMove;
    }


    public boolean moveToNext() {
        way++;
        if (!node.canMove()) {
            return false;
        }

        Node target;
        if (node.getNext() instanceof Space) {
            target = node.getNext();
            Step.swap(target, node);
            return true;
        }

        return false;
    }

    public boolean moveToPrev() {
        way++;
        if (!node.canMove()) {
            return false;
        }

        Node target;
        if (node.getPrev() instanceof Space) {
            target = node.getPrev();
            Step.swap(node, target);
            return true;
        }

        return false;
    }

    public boolean moveToNextNext() {
        way++;
        if (!node.canMove()) {
            return false;
        }

        Node target;
        if (node.getNext() != null && node.getNext().getNext() instanceof Space) {
            target = node.getNext().getNext();
            Step.swapWithGap(target, node);
            return true;
        }

        return false;
    }

    public boolean moveToPrevPrev() {
        way++;
        if (!node.canMove()) {
            return false;
        }

        Node target;
        if (node.getPrev() != null && node.getPrev().getPrev() instanceof Space) {
            target = node.getPrev().getPrev();
            Step.swapWithGap(node, target);
            return true;
        }

        return false;
    }


}
