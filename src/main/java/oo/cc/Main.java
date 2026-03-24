package oo.cc;

import oo.cc.nodes.Node;
import oo.cc.nodes.NodeFactory;
import oo.cc.strategies.benchmark.BenchmarkDecorator;
import oo.cc.strategies.benchmark.BenchmarkResult;
import oo.cc.strategies.exhaustive.ExhaustiveStrategy;
import oo.cc.strategies.Strategy;
import oo.cc.strategies.thebest.TheBestStrategy;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("MoveDemo Performance Benchmark (US1)");
        System.out.println("------------------------------------");
        System.out.printf("%-5s | %-15s | %-10s | %-15s | %-10s%n", "N", "Strategy", "Steps", "Time (ns)", "Memory (B)");
        System.out.println("-------------------------------------------------------------------------");

        for (int i = 1; i <= 26; i++) {
            runBenchmark(i, "TheBest");
        }

        System.out.println("\nExhaustive Strategy Benchmark (N=1 to 10)");
        System.out.println("-----------------------------------------");
        for (int i = 1; i <= 10; i++) {
            runBenchmark(i, "Exhaustive");
        }
    }

    private static void runBenchmark(int n, String strategyType) {
        List<Node> nodeList = NodeFactory.createNodeChain(n);
        Strategy baseStrategy;
        
        if ("Exhaustive".equalsIgnoreCase(strategyType)) {
            baseStrategy = new ExhaustiveStrategy(nodeList);
        } else {
            baseStrategy = new TheBestStrategy(nodeList);
        }

        BenchmarkDecorator decorator = new BenchmarkDecorator(baseStrategy, strategyType, n);
        decorator.exec();
        BenchmarkResult result = decorator.getResult();

        System.out.printf("%-5d | %-15s | %-10d | %-15d | %-10d%n", 
            result.n(), result.strategyName(), result.steps(), result.timeInNano(), result.memoryUsedInBytes());
    }
}
