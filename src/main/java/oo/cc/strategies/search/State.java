package oo.cc.strategies.search;

import java.util.Objects;

/**
 * 表示搜尋空間中的一個狀態。
 * 用於 BFS、A* 等演算法。
 */
public record State(
    String board,
    int emptyPos,
    int g, // 從起點到目前的步數
    int h, // 啟發函數估算到終點的步數
    State parent
) {
    public int f() {
        return g + h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return emptyPos == state.emptyPos && Objects.equals(board, state.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, emptyPos);
    }
}
