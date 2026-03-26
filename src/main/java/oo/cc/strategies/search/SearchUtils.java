package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SearchUtils {

    private static final Map<Integer, String> goalCache = new ConcurrentHashMap<>();

    public static String getBoardString(List<Node> nodes) {
        char[] chars = new char[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node instanceof Space) {
                chars[i] = 'S';
            } else {
                switch (node.getDirect()) {
                    case LEFT: chars[i] = 'L'; break;
                    case RIGHT: chars[i] = 'R'; break;
                    default: break;
                }
            }
        }
        return new String(chars);
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
        int len = board.length();
        String goal = goalCache.computeIfAbsent(len, l -> {
            int n = (l - 1) / 2;
            return "R".repeat(n) + "S" + "L".repeat(n);
        });
        return board.equals(goal);
    }

    public static List<State> getNeighbors(State state) {
        List<State> jumps = new ArrayList<>();
        List<State> slides = new ArrayList<>();
        String board = state.board();
        int spaceIdx = state.emptyPos();

        for (int i = 0; i < board.length(); i++) {
            char piece = board.charAt(i);
            if (piece == 'S') continue;

            int dist = spaceIdx - i;

            if (piece == 'L') {
                if (dist == 1) {
                    String newBoard = swap(board, i, spaceIdx);
                    slides.add(new State(newBoard, i, state.g() + 1, Heuristics.getHeuristic(newBoard), state));
                } else if (dist == 2 && board.charAt(i + 1) == 'R') {
                    String newBoard = swap(board, i, spaceIdx);
                    jumps.add(new State(newBoard, i, state.g() + 1, Heuristics.getHeuristic(newBoard), state));
                }
            } else if (piece == 'R') {
                if (dist == -1) {
                    String newBoard = swap(board, i, spaceIdx);
                    slides.add(new State(newBoard, i, state.g() + 1, Heuristics.getHeuristic(newBoard), state));
                } else if (dist == -2 && board.charAt(i - 1) == 'L') {
                    String newBoard = swap(board, i, spaceIdx);
                    jumps.add(new State(newBoard, i, state.g() + 1, Heuristics.getHeuristic(newBoard), state));
                }
            }
        }
        
        // 核心優化：如果可以跳躍，則優先考慮跳躍。在許多情況下，這能大幅減少搜尋空間。
        if (!jumps.isEmpty()) {
            return jumps;
        }
        return slides;
    }

    private static String swap(String s, int i, int j) {
        char[] arr = s.toCharArray();
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return new String(arr);
    }
}
