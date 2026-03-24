package oo.cc;

import oo.cc.strategies.search.Heuristics;
import oo.cc.nodes.Node;
import oo.cc.nodes.NodeFactory;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeuristicTest {

    @Test
    public void testHeuristicFunction() {
        // 簡單測試 A* 的啟發函數是否返回非負值
        int n = 5;
        List<Node> nodeList = NodeFactory.createNodeChain(n);
        
        int h = Heuristics.getHeuristic(nodeList);
        assertTrue(h >= 0, "Heuristic value should be non-negative");
    }
}
