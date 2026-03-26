package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.strategies.Strategy;
import java.util.*;

/**
 * 實作 BFS 搜尋演算法以解決 MoveDemo 問題。
 */
public class BFSStrategy implements Strategy {

    private final List<Node> initialNodes;

    public BFSStrategy(List<Node> initialNodes) {
        this.initialNodes = initialNodes;
    }

    @Override
    public int exec() {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        String initialBoard = SearchUtils.getBoardString(initialNodes);
        int initialSpace = SearchUtils.getSpaceIndex(initialNodes);
        State start = new State(initialBoard, initialSpace, 0, 0, null);
        
        queue.add(start);
        visited.add(start.board());

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (SearchUtils.isGoal(current.board())) {
                return current.g();
            }

            for (State neighbor : SearchUtils.getNeighbors(current)) {
                if (!visited.contains(neighbor.board())) {
                    visited.add(neighbor.board());
                    queue.add(neighbor);
                }
            }
        }
        return -1;
    }
}
