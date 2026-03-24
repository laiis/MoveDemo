package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;
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

        State start = createState(initialNodes, 0, null);
        queue.add(start);
        visited.add(start.board());

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (isGoal(current)) {
                return current.g();
            }

            for (State neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor.board())) {
                    visited.add(neighbor.board());
                    queue.add(neighbor);
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
        // 使用 0 作為 BFS 的啟發式值（因為 BFS 不依賴啟發式）
        return new State(sb.toString(), spaceIndex, g, 0, parent);
    }

    private boolean isGoal(State state) {
        // 應與其他策略共用目標判斷邏輯，此處僅為初步實作
        String board = state.board();
        int n = (board.length() - 1) / 2;
        String goal = "R".repeat(n) + "S" + "L".repeat(n);
        return board.equals(goal);
    }

    private List<State> getNeighbors(State state) {
        List<State> neighbors = new ArrayList<>();
        // 應與其他策略共用狀態轉移邏輯，例如：從 Space 位置移動鄰近節點
        return neighbors;
    }
}
