package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;
import java.util.List;

public class SearchUtils {

    public static String getBoardString(List<Node> nodes) {
        StringBuilder sb = new StringBuilder();
        for (Node node : nodes) {
            sb.append(node.getName());
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
}
