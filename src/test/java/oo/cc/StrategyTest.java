package oo.cc;

import oo.cc.nodes.Node;
import oo.cc.nodes.Space;
import oo.cc.steps.Direct;
import oo.cc.strategies.Strategy;
import oo.cc.strategies.benchmark.BenchmarkDecorator;
import oo.cc.strategies.benchmark.BenchmarkResult;
import oo.cc.strategies.thebest.TheBestStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

public class StrategyTest {

    private List<Node> genNode(int size) {
        List<Node> nodeList = new ArrayList<>();
        // 左側節點 (a, b, c...)
        for (int i = 0; i < size; i++) {
            Node node = new Node(String.valueOf((char) ('a' + i)), Direct.LEFT);
            nodeList.add(node);
        }

        nodeList.add(new Space("0", Direct.NONE));

        for (int i = 0; i < size; i++) {
            Node node = new Node(String.valueOf((char) ('A' + i)), Direct.RIGHT);
            nodeList.add(node);
        }

        return nodeList;
    }

    private void orderNode(List<Node> nodeList) {
        Node temp = nodeList.get(0);
        for (int i = 1; i < nodeList.size(); i++) {
            temp.setNext(nodeList.get(i));
            temp = nodeList.get(i);
        }

        temp = nodeList.get(nodeList.size() - 1);
        for (int i = nodeList.size() - 2; i >= 0; i--) {
            temp.setPrev(nodeList.get(i));
            temp = nodeList.get(i);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 10, 20})
    @DisplayName("驗證 TheBestStrategy 符合 N*(N+2) 公式")
    public void testTheBestStrategy(int n) {
        List<Node> nodeList = genNode(n);
        orderNode(nodeList);
        Strategy strategy = new TheBestStrategy(nodeList);
        
        int expectedSteps = n * (n + 2);
        int actualSteps = strategy.exec();
        
        Assertions.assertEquals(expectedSteps, actualSteps, "N=" + n + " 時步數不符");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("驗證 BenchmarkDecorator 正確攔截指標")
    public void testBenchmarkDecorator(int n) {
        List<Node> nodeList = genNode(n);
        orderNode(nodeList);
        Strategy baseStrategy = new TheBestStrategy(nodeList);
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
