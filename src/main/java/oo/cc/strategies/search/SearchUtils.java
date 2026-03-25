package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;

import java.util.ArrayList;
import java.util.List;

public class SearchUtils {

    public static String getBoardString(List<Node> nodes) {
        StringBuilder sb = new StringBuilder();
        for (Node node : nodes) {
            if (node instanceof Space) {
                sb.append('S');
            } else {
                switch (node.getDirect()) {
                    case LEFT:
                        sb.append('L');
                        break;
                    case RIGHT:
                        sb.append('R');
                        break;
                    default:
                        // Or handle error appropriately
                        break;
                }
            }
        }
        return sb.toString();
    }

    public static int getSpaceIndex(List<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) instanceof Space) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isGoal(String board) {
        int n = (board.length() - 1) / 2;
        String goal = "R".repeat(n) + "S" + "L".repeat(n);
        return board.equals(goal);
    }

    public static List<State> getNeighbors(State state) {
        List<State> neighbors = new ArrayList<>();
        String board = state.board();
        int spaceIdx = state.spaceIdx();

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

    private static String swap(String s, int i, int j) {
        char[] arr = s.toCharArray();
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return new String(arr);
    }
}
