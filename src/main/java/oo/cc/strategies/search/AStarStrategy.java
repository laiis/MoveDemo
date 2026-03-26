package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.strategies.Strategy;
import java.util.*;

/**
 * 實作優化後的 A* 搜尋演算法。
 * 使用完美啟發函數，並結合 SearchUtils 的跳躍優先剪枝策略。
 */
public class AStarStrategy implements Strategy {

    private final List<Node> initialNodes;

    public AStarStrategy(List<Node> initialNodes) {
        this.initialNodes = initialNodes;
    }

    @Override
    public int exec() {
        // 使用 PriorityQueue 以確保 A* 性質
        // 當 f 值相同時，優先處理 h 值較小 (更接近目標) 的狀態
        PriorityQueue<State> openSet = new PriorityQueue<>(
            Comparator.comparingInt(State::f)
                      .thenComparingInt(State::h)
        );
        
        // 紀錄已探索過的狀態與其最小 gScore
        Map<String, Integer> gScore = new HashMap<>();
        
        String initialBoard = SearchUtils.getBoardString(initialNodes);
        int initialSpace = SearchUtils.getSpaceIndex(initialNodes);
        State start = new State(initialBoard, initialSpace, 0, Heuristics.getHeuristic(initialBoard), null);
        
        openSet.add(start);
        gScore.put(start.board(), 0);

        while (!openSet.isEmpty()) {
            State current = openSet.poll();

            // 若目前狀態的 g 已經大於紀錄中的，代表之前已經找到更短的路徑抵達此狀態
            if (current.g() > gScore.getOrDefault(current.board(), Integer.MAX_VALUE)) {
                continue;
            }

            if (SearchUtils.isGoal(current.board())) {
                return current.g();
            }

            for (State neighbor : SearchUtils.getNeighbors(current)) {
                int tentativeGScore = current.g() + 1;
                
                if (tentativeGScore < gScore.getOrDefault(neighbor.board(), Integer.MAX_VALUE)) {
                    gScore.put(neighbor.board(), tentativeGScore);
                    openSet.add(neighbor);
                }
            }
        }
        return -1;
    }
}
