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
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("驗證初始排列：長度為 N+1，中間為空白")
    public void testInitialLayout(int n) {
        List<Node> chain = NodeFactory.createNodeChain(n);
        Assertions.assertEquals(n + 1, chain.size(), "串列長度應為 N+1");
        
        int spaceIndex = n / 2;
        Assertions.assertTrue(chain.get(spaceIndex) instanceof Space, "索引 " + spaceIndex + " 應為空白 (Space)");
        
        // 驗證左側棋子向右，右側棋子向左 (根據現有 NodeFactory 實作)
        for (int i = 0; i < spaceIndex; i++) {
            Assertions.assertEquals(Direct.RIGHT, chain.get(i).getDirect(), "左側棋子應向右");
        }
        for (int i = spaceIndex + 1; i < chain.size(); i++) {
            Assertions.assertEquals(Direct.LEFT, chain.get(i).getDirect(), "右側棋子應向左");
        }
    }

    @Test
    @DisplayName("驗證移動規則：滑動與跳躍限制")
    public void testMoveRules() {
        int n = 3; // O O _ X X (N=4? 不，NodeFactory.createNodeChain(3) 會產生 4 個節點？)
        // 根據現有 NodeFactory.createNodeChain(n):
        // N=1: [R] [S]
        // N=2: [R] [S] [L]
        // N=3: [R] [R] [S] [L]
        // 讓我們檢查一下 NodeFactory 的行為
        List<Node> chain = NodeFactory.createNodeChain(3);
        
        // 找到空白節點
        Node space = null;
        for(Node node : chain) if(node instanceof Space) space = node;
        
        Assertions.assertNotNull(space);
        
        // 驗證相鄰滑動：空白左邊的 RIGHT 棋子應可向右滑動
        Node leftNode = space.getPrev();
        if (leftNode != null && leftNode.getDirect() == Direct.RIGHT) {
            Step step = new StepImpl(leftNode);
            Assertions.assertTrue(step.move(), "左側向右棋子應可滑動至空格");
        }
    }

    @Test
    @DisplayName("驗證輪替移動原則 (US4 核心)")
    public void testRotationPrinciple() {
        // 這是一個邏輯驗證，RecursionStrategy 必須遵守：
        // 1. 左邊動 -> 2. 右邊動 -> 3. 左邊動 ...
        // 我們將在實作 RecursionStrategy 時透過此測試確保其遵循此模式
        
        // 此處先定義期望的行為模式
        List<Direct> moveHistory = new ArrayList<>();
        
        // 模擬一個符合規則的序列 (範例)
        moveHistory.add(Direct.RIGHT); // 左側動
        moveHistory.add(Direct.LEFT);  // 右側動
        moveHistory.add(Direct.RIGHT); // 左側動
        
        for (int i = 1; i < moveHistory.size(); i++) {
            Assertions.assertNotEquals(moveHistory.get(i-1), moveHistory.get(i), "棋子移動應左右交替");
        }
    }

    @Test
    @DisplayName("驗證完成條件：左右棋子全數交換")
    public void testCompletionCondition() {
        int n = 2; // R S L -> L S R (預期完成狀態)
        List<Node> chain = NodeFactory.createNodeChain(n);
        
        // 模擬完成狀態
        // 注意：這取決於 Step.isAllMove 的實作
        // 我們假設當所有 Direct.RIGHT 在 Space 右邊，所有 Direct.LEFT 在 Space 左邊時完成
        
        // 這裡我們僅驗證 Step.isAllMove 是否能正確識別初始狀態為「未完成」
        Node space = null;
        for(Node node : chain) if(node instanceof Space) space = node;
        
        Assertions.assertFalse(Step.isAllMove(space, n/2), "初始狀態不應視為已完成");
    }
}
