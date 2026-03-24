package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.strategies.Strategy;
import java.util.*;

public class BFSStrategy implements Strategy {

    private final List<Node> initialNodes;

    public BFSStrategy(List<Node> initialNodes) {
        this.initialNodes = initialNodes;
    }

    @Override
    public int exec() {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        State start = new State(SearchUtils.getBoardString(initialNodes), SearchUtils.getSpaceIndex(initialNodes), 0, 0, null);
        queue.add(start);
        visited.add(start.board());

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (SearchUtils.isGoal(current.board())) {
                return current.g();
            }

            for (State neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor.board())) {
                    visited.add(neighbor.board());
                    queue.add(neighbor);
                }
            }
        }
        return -1;
    }

    private List<State> getNeighbors(State state) {
        // 此處仍需實作節點移動邏輯，後續階段將進一步重構
        return new ArrayList<>();
    }
}
