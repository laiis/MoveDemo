package oo.cc;

import oo.cc.nodes.Node;
import oo.cc.nodes.NodeFactory;
import oo.cc.nodes.Space;
import oo.cc.steps.Direct;
import oo.cc.steps.Step;
import oo.cc.steps.StepImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

public class PieceRuleTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 5, 10})
    @DisplayName("驗證初始排列：長度為 2N+1，中間為空白")
    public void testInitialLayout(int n) {
        List<Node> chain = NodeFactory.createNodeChain(n);
        Assertions.assertEquals(2 * n + 1, chain.size(), "串列長度應為 2N+1");

        // 根據 NodeFactory: n 個 L, 1 個 S, n 個 R
        int spaceIndex = n; 
        Assertions.assertTrue(chain.get(spaceIndex) instanceof Space, "索引 " + spaceIndex + " 應為空白 (Space)");

        // 驗證左側棋子為 LEFT (預期向右移動)，右側棋子為 RIGHT (預期向左移動)
        for (int i = 0; i < spaceIndex; i++) {
            Assertions.assertEquals(Direct.LEFT, chain.get(i).getDirect(), "左側棋子應為 LEFT");
        }
        for (int i = spaceIndex + 1; i < chain.size(); i++) {
            Assertions.assertEquals(Direct.RIGHT, chain.get(i).getDirect(), "右側棋子應為 RIGHT");
        }
    }

    @Test
    @DisplayName("驗證移動規則：滑動與跳躍限制")
    public void testMoveRules() {
        int n = 3; 
        List<Node> chain = NodeFactory.createNodeChain(n);

        // 找到空白節點
        Node space = null;
        for (Node node : chain) if (node instanceof Space) space = node;

        Assertions.assertNotNull(space);

        // 驗證相鄰滑動：空白左邊的 LEFT 棋子應可向右滑動
        Node leftOfSpace = space.getPrev();
        if (leftOfSpace != null && leftOfSpace.getDirect() == Direct.LEFT) {
            Step step = new StepImpl(leftOfSpace);
            Assertions.assertTrue(step.move(), "左側 LEFT 棋子應可滑動至右側空格");
        }

        // 驗證相鄰滑動：空白右邊的 RIGHT 棋子應可向左滑動
        // 重新獲取 chain 因為 swap 可能改變了位置
        chain = NodeFactory.createNodeChain(n);
        for (Node node : chain) if (node instanceof Space) space = node;
        Node rightOfSpace = space.getNext();
        if (rightOfSpace != null && rightOfSpace.getDirect() == Direct.RIGHT) {
            Step step = new StepImpl(rightOfSpace);
            Assertions.assertTrue(step.move(), "右側 RIGHT 棋子應可滑動至左側空格");
        }
    }

    @Test
    @DisplayName("驗證跳躍限制：不可跳過同色棋子")
    public void testJumpRestriction() {
        // 初始狀態: L L L S R R R (n=3)
        // L 棋子 (index 1) 前方 (index 2) 是 L，跳過它到 index 3 (S) 應被禁止
        int n = 3;
        List<Node> chain = NodeFactory.createNodeChain(n);

        // 嘗試讓 index 1 的 L 棋子跳過 index 2 的 L 棋子到達 index 3 (Space)
        Node lPiece = chain.get(1); 
        Step step = new StepImpl(lPiece);

        // 根據新規範，此動作應回傳 false
        boolean canJump = step.move(); 

        Assertions.assertFalse(canJump, "棋子不應跳過相同顏色的棋子");
    }

    @Test
    @DisplayName("驗證完成條件：左右棋子全數交換")
    public void testCompletionCondition() {
        int n = 2; 
        List<Node> chain = NodeFactory.createNodeChain(n);

        Node space = null;
        int spaceIndex = -1;
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i) instanceof Space) {
                space = chain.get(i);
                spaceIndex = i;
                break;
            }
        }

        Assertions.assertFalse(Step.isAllMove(space, spaceIndex), "初始狀態不應視為已完成");
    }
}
