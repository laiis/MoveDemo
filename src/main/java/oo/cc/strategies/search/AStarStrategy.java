package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;
import oo.cc.strategies.Strategy;
import java.util.*;

public class AStarStrategy implements Strategy {

    private final List<Node> initialNodes;
    private final int n;

    public AStarStrategy(List<Node> initialNodes) {
        this.initialNodes = initialNodes;
        this.n = (initialNodes.size() - 1) / 2;
    }

    @Override
    public int exec() {
        PriorityQueue<State> openSet = new PriorityQueue<>(Comparator.comparingInt(State::f));
        Map<String, Integer> gScore = new HashMap<>();
        
        State start = createState(initialNodes, 0, null);
        openSet.add(start);
        gScore.put(start.board(), 0);

        while (!openSet.isEmpty()) {
            State current = openSet.poll();

            if (isGoal(current)) {
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
        return -1; // 無解
    }

    private State createState(List<Node> nodes, int g, State parent) {
        StringBuilder sb = new StringBuilder();
        int spaceIndex = -1;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) instanceof Space) {
                spaceIndex = i;
            }
            sb.append(nodes.get(i).getName());
        }
        return new State(sb.toString(), spaceIndex, g, Heuristics.getHeuristic(nodes), parent);
    }

    private boolean isGoal(State state) {
        // 根據 MoveDemo 規則判斷是否到達目標狀態
        // 目標狀態：所有左側節點移動到右側，所有右側節點移動到左側
        return true; 
    }

    private List<State> getNeighbors(State state) {
        List<State> neighbors = new ArrayList<>();
        // 實作節點移動邏輯
        return neighbors;
    }
}
