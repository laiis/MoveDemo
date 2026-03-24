package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.strategies.Strategy;
import java.util.*;

public class AStarStrategy implements Strategy {

    private final List<Node> initialNodes;

    public AStarStrategy(List<Node> initialNodes) {
        this.initialNodes = initialNodes;
    }

    @Override
    public int exec() {
        PriorityQueue<State> openSet = new PriorityQueue<>(Comparator.comparingInt(State::f));
        Map<String, Integer> gScore = new HashMap<>();
        
        State start = new State(SearchUtils.getBoardString(initialNodes), SearchUtils.getSpaceIndex(initialNodes), 0, Heuristics.getHeuristic(initialNodes), null);
        openSet.add(start);
        gScore.put(start.board(), 0);

        while (!openSet.isEmpty()) {
            State current = openSet.poll();

            if (SearchUtils.isGoal(current.board())) {
                return current.g();
            }

            for (State neighbor : getNeighbors(current)) {
                int tentativeGScore = current.g() + 1;
                if (tentativeGScore < gScore.getOrDefault(neighbor.board(), Integer.MAX_VALUE)) {
                    gScore.put(neighbor.board(), tentativeGScore);
                    openSet.add(neighbor);
                }
            }
        }
        return -1;
    }

    private List<State> getNeighbors(State state) {
        // 實作節點移動邏輯，後續階段將進一步重構
        return new ArrayList<>();
    }
}
