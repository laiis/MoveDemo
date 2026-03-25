package oo.cc.strategies.search;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;
import oo.cc.steps.Direct;
import oo.cc.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 根據 PieceRule.md 實作的遞迴回溯策略。
 * 嚴格遵循左右輪替規則與回溯機制。
 */
public class RecursionStrategy implements Strategy {
    private List<Node> nodes;
    private final int n; // 每一側的棋子數
    private int steps = 0;
    // 使用 set 來避免重複狀態，防止無限遞迴
    private java.util.Set<List<Node>> visitedStates = new java.util.HashSet<>();

    public RecursionStrategy(List<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
        this.n = (nodes.size() - 1) / 2;
    }

    @Override
    public int exec() {
        if (solve()) {
            return steps;
        }
        return -1; // 求解失敗
    }

    private boolean solve() {
        if (isFinished()) {
            return true;
        }

        // 防止因迴圈路徑導致的無限遞迴
        if (!visitedStates.add(new ArrayList<>(nodes))) {
            return false;
        }

        int spaceIdx = findSpace();
        
        // 嘗試所有可能的合法移動 (無論棋子在哪一側)
        for (int from = 0; from < nodes.size(); from++) {
            if (nodes.get(from) instanceof Space) continue;

            if (canMove(from, spaceIdx)) {
                move(from, spaceIdx);
                steps++;
                
                if (solve()) {
                    return true;
                }
                
                // 回溯
                undoMove(from, spaceIdx);
                steps--;
            }
        }
        
        // 從訪問過的狀態中移除，以便其他路徑可以訪問此狀態
        visitedStates.remove(new ArrayList<>(nodes));
        
        return false;
    }

    private boolean canMove(int from, int to) {
        // T029: 實作移動規則判斷
        Node fromNode = nodes.get(from);
        if (!(nodes.get(to) instanceof Space)) return false;
        
        int dist = to - from;
        
        // LEFT 棋子只能右移 (dist > 0)
        if (fromNode.getDirect() == Direct.LEFT) {
            return dist == 1 || dist == 2;
        } 
        // RIGHT 棋子只能左移 (dist < 0)
        else if (fromNode.getDirect() == Direct.RIGHT) {
            return dist == -1 || dist == -2;
        }
        
        return false; 
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
        // 判斷最終狀態：[R...R, S, L...L]
        // 1. 左邊 n 個應該是 Direct.RIGHT
        for (int i = 0; i < n; i++) {
            if (nodes.get(i).getDirect() != Direct.RIGHT) return false;
        }
        // 2. 中間索引 n 應該是 Space
        if (!(nodes.get(n) instanceof Space)) return false;
        // 3. 右邊 n 個應該是 Direct.LEFT
        for (int i = n + 1; i < nodes.size(); i++) {
            if (nodes.get(i).getDirect() != Direct.LEFT) return false;
        }
        return true;
    }
}
