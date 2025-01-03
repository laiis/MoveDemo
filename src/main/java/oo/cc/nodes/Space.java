package oo.cc.nodes;

import oo.cc.steps.Direct;

public final class Space extends Node {

    public Space(String name, Direct direct) {
        super(name, direct);
    }

    @Override
    public boolean canMove() {
        return false;
    }
}
