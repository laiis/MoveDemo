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
        List<State> neighbors = new ArrayList<>();
        String board = state.board();
        int spaceIdx = board.indexOf('S');
        int n = (board.length() - 1) / 2;

        for (int i = 0; i < board.length(); i++) {
            char piece = board.charAt(i);
            if (piece == 'S') {
                continue;
            }

            int dist = spaceIdx - i;

            // 'L' pieces can only move right (dist > 0)
            if (piece == 'L' && (dist == 1 || dist == 2)) {
                String newBoard = swap(board, i, spaceIdx);
                neighbors.add(new State(newBoard, i, state.g() + 1, 0, state));
            }
            // 'R' pieces can only move left (dist < 0)
            else if (piece == 'R' && (dist == -1 || dist == -2)) {
                String newBoard = swap(board, i, spaceIdx);
                neighbors.add(new State(newBoard, i, state.g() + 1, 0, state));
            }
        }
        return neighbors;
    }

    private String swap(String s, int i, int j) {
        char[] arr = s.toCharArray();
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return new String(arr);
    }
}
