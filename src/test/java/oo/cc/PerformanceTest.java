package oo.cc;

import oo.cc.nodes.Node;
import oo.cc.nodes.NodeFactory;
import oo.cc.strategies.benchmark.BenchmarkDecorator;
import oo.cc.strategies.benchmark.BenchmarkResult;
import oo.cc.strategies.thebest.TheBestStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PerformanceTest {

    @Test
    public void testPerformanceAtN26() {
        int n = 26;
        List<Node> nodeList = NodeFactory.createNodeChain(n);
        TheBestStrategy strategy = new TheBestStrategy(nodeList);

        // 使用正確的建構子
        BenchmarkDecorator decorator = new BenchmarkDecorator(strategy, "TheBestStrategy", n);

        // 執行基準測試
        decorator.exec();
        BenchmarkResult result = decorator.getResult();

        // 根據基準測試結果 (Phase 3)，N=26 約為 4ms。
        // 設定一個寬鬆的閾值，例如 100ms。
        assertTrue(result.timeInMillis() < 100, "Performance at N=26 is too slow: " + result.timeInMillis() + "ms");
    }

}
