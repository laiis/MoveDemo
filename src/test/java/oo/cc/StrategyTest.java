package oo.cc;

import oo.cc.nodes.Node;
import oo.cc.nodes.NodeFactory;
import oo.cc.strategies.Strategy;
import oo.cc.strategies.benchmark.BenchmarkDecorator;
import oo.cc.strategies.benchmark.BenchmarkResult;
import oo.cc.strategies.thebest.TheBestStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

public class StrategyTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 10, 20})
    @DisplayName("驗證 TheBestStrategy 符合 N*(N+2) 公式")
    public void testTheBestStrategy(int n) {
        Strategy strategy = new TheBestStrategy(NodeFactory.createNodeChain(n));
        
        int expectedSteps = n * (n + 2);
        int actualSteps = strategy.exec();
        
        Assertions.assertEquals(expectedSteps, actualSteps, "N=" + n + " 時步數不符");
    }

    // @ParameterizedTest
    // @ValueSource(ints = {1, 2, 3, 4, 5})
    // @DisplayName("驗證 BFSStrategy 符合 N*(N+2) 公式")
    // public void testBFSStrategy(int n) {
    //     Strategy strategy = new BFSStrategy(NodeFactory.createNodeChain(n));
    //     int actualSteps = strategy.exec();
    //     Assertions.assertEquals(n * (n + 2), actualSteps);
    // }

    // @ParameterizedTest
    // @ValueSource(ints = {1, 2, 3, 4, 5})
    // @DisplayName("驗證 AStarStrategy 符合 N*(N+2) 公式")
    // public void testAStarStrategy(int n) {
    //     Strategy strategy = new AStarStrategy(NodeFactory.createNodeChain(n));
    //     int actualSteps = strategy.exec();
    //     Assertions.assertEquals(n * (n + 2), actualSteps);
    // }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("驗證 BenchmarkDecorator 正確攔截指標")
    public void testBenchmarkDecorator(int n) {
        Strategy baseStrategy = new TheBestStrategy(NodeFactory.createNodeChain(n));
        BenchmarkDecorator decorator = new BenchmarkDecorator(baseStrategy, "TheBest", n);
        
        int expectedSteps = n * (n + 2);
        int actualSteps = decorator.exec();
        BenchmarkResult result = decorator.getResult();
        
        Assertions.assertEquals(expectedSteps, actualSteps);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("TheBest", result.strategyName());
        Assertions.assertTrue(result.timeInNano() > 0);
    }
}
