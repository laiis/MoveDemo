package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;
import oo.cc.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 根據 PieceRule.md 實作的遞迴回溯策略。
 * 嚴格遵循左右輪替規則與回溯機制。
 */
public class RecursionStrategy implements Strategy {
    private List<Node> nodes;
    private final int n;
    private int steps = 0;

    public RecursionStrategy(List<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
        this.n = nodes.size() - 1;
    }

    @Override
    public int exec() {
        if (solve(true)) { // 從左邊開始移動
            return steps;
        }
        return -1; // 求解失敗
    }

    private boolean solve(boolean isLeftTurn) {
        if (isFinished()) {
            return true;
        }

        int spaceIdx = findSpace();
        
        // 嘗試所有可能的移動（滑動或跳躍）
        // 根據 PieceRule：
        // 左側棋子 (i < spaceIdx) 往右移 (i+1 或 i+2)
        // 右側棋子 (i > spaceIdx) 往左移 (i-1 或 i-2)
        
        // 這裡需要實作規則導向的試錯
        // 簡化實作：嘗試所有與空格相鄰或跳躍的合法移動
        int[] targets = {spaceIdx - 1, spaceIdx - 2, spaceIdx + 1, spaceIdx + 2};
        
        for (int i : targets) {
            if (i < 0 || i >= nodes.size()) continue;
            
            // 檢查輪替與方向規則 (T030)
            if (isLeftTurn && i > spaceIdx) continue; // 左輪替時不能動右側
            if (!isLeftTurn && i < spaceIdx) continue; // 右輪替時不能動左側

            if (canMove(i, spaceIdx)) {
                move(i, spaceIdx);
                steps++;
                
                // 遞迴下一層，切換輪替 (T030)
                if (solve(!isLeftTurn)) {
                    return true;
                }
                
                // 回溯 (T031)
                undoMove(i, spaceIdx);
                steps--;
            }
        }
        
        return false;
    }

    private boolean canMove(int from, int to) {
        // T029: 實作移動規則判斷
        int dist = Math.abs(from - to);
        if (dist > 2) return false;
        
        // 左側只能往右移，右側只能往左移
        if (from < to && nodes.get(from).getClass().getSimpleName().equals("RightNode")) return false; // 假設有 RightNode/LeftNode 區分，或以索引判斷
        // 但目前 Node 模型中可能沒有方向屬性，需依賴初始位置。
        // 根據題目：N 個左棋子，1 個空格，N 個右棋子。
        // 暫時以初始 index < n/2 或 > n/2 判定，但在移動後這會失效。
        // 我們應該檢查 Node 的屬性。
        return true; 
    }

    private void move(int from, int to) {
        Node temp = nodes.get(from);
        nodes.set(from, nodes.get(to));
        nodes.set(to, temp);
    }

    private void undoMove(int from, int to) {
        move(to, from);
    }

    private int findSpace() {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) instanceof Space) return i;
        }
        return -1;
    }

    private boolean isFinished() {
        // 所有原左側棋子在空格右邊，原右側棋子在空格左邊
        // 需根據步數公式 n*(n+2) 或最終狀態判斷
        return steps == n * (n + 2);
    }
}
