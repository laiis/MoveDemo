package oo.cc.nodes;

public final class Space extends Node {

    public Space(String name) {
        super(name);
    }

    @Override
    public boolean canMove() {
        return false;
    }
}
