package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import java.util.List;

/**
 * 實作針對 MoveDemo 遊戲的完美啟發函數 (Perfect Heuristic)。
 * 步數 h(s) = 總距離(s) - 反轉數(s)。
 * 此函數能精確預測到達目標所需的剩餘步數，使 A* 搜尋效率極大化。
 */
public class Heuristics {
    public static int getHeuristic(List<Node> nodes) {
        return getHeuristic(SearchUtils.getBoardString(nodes));
    }

    public static int getHeuristic(String board) {
        int n = (board.length() - 1) / 2;
        
        long sumTargetL = (long) (n + 1 + 2 * n) * n / 2; // n+1 到 2n 的總和
        long sumTargetR = (long) (0 + n - 1) * n / 2;     // 0 到 n-1 的總和
        
        long currentSumL = 0;
        long currentSumR = 0;
        int inversions = 0;
        int lSeenSoFar = 0;

        for (int i = 0; i < board.length(); i++) {
            char c = board.charAt(i);
            if (c == 'L') {
                currentSumL += i;
                lSeenSoFar++;
            } else if (c == 'R') {
                currentSumR += i;
                inversions += lSeenSoFar;
            }
        }

        long distL = sumTargetL - currentSumL;
        long distR = currentSumR - sumTargetR;
        
        return (int) (distL + distR - inversions);
    }
}
