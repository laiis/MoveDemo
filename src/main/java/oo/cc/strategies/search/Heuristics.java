package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;
import java.util.List;

/**
 * 定義 A* 演算法的啟發函數。
 * 目標是將左側節點 (Left) 移動到右側，右側節點 (Right) 移動到左側。
 * 啟發函數估算剩餘需要移動的節點數量。
 */
public class Heuristics {
    public static int getHeuristic(List<Node> nodes) {
        int misplacedCount = 0;
        int n = (nodes.size() - 1) / 2;

        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node instanceof Space) continue;

            // 根據 MoveDemo 規則：
            // 前半段 (0 到 n-1) 應存放右側節點 (Right)
            // 後半段 (n+1 到 2n) 應存放左側節點 (Left)
            
            boolean isLeftNode = "LEFT".equals(node.getDirect().name());
            
            if (i < n) {
                // 前半段應該是 Right
                if (isLeftNode) misplacedCount++;
            } else if (i > n) {
                // 後半段應該是 Left
                if (!isLeftNode) misplacedCount++;
            }
        }
        return misplacedCount;
    }
}
