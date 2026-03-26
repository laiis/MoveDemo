package oo.cc;

import oo.cc.nodes.Node;
import oo.cc.nodes.NodeFactory;
import oo.cc.strategies.benchmark.BenchmarkDecorator;
import oo.cc.strategies.benchmark.BenchmarkResult;
import oo.cc.strategies.exhaustive.ExhaustiveStrategy;
import oo.cc.strategies.Strategy;
import oo.cc.strategies.search.AStarStrategy;
import oo.cc.strategies.search.BFSStrategy;
import oo.cc.strategies.search.RecursionStrategy;
import oo.cc.strategies.thebest.TheBestStrategy;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("MoveDemo Performance Benchmark (US1 & US3)");
        System.out.println("-------------------------------------------");
        System.out.printf("%-5s | %-15s | %-10s | %-15s | %-10s%n", "N", "Strategy", "Steps", "Time (ns)", "Memory (B)");
        System.out.println("-------------------------------------------------------------------------");

        for (int i = 1; i <= 26; i++) {
            runBenchmark(i, "TheBest");
            if (i <= 10) {
                runBenchmark(i, "BFS");
                runBenchmark(i, "AStar");
            }
            if (i <= 5) { // Recursion is slower
                runBenchmark(i, "EXHAUSTIVE");
            }
        }
    }

    private static void runBenchmark(int n, String strategyType) {
        List<Node> nodeList = NodeFactory.createNodeChain(n);
        Strategy baseStrategy;
        
        switch (strategyType.toUpperCase()) {
            case "EXHAUSTIVE":
                baseStrategy = new ExhaustiveStrategy(nodeList);
                break;
            case "BFS":
                baseStrategy = new BFSStrategy(nodeList);
                break;
            case "ASTAR":
                baseStrategy = new AStarStrategy(nodeList);
                break;
            case "RECURSION":
                baseStrategy = new RecursionStrategy(nodeList);
                break;
            default:
                baseStrategy = new TheBestStrategy(nodeList);
                break;
        }

        BenchmarkDecorator decorator = new BenchmarkDecorator(baseStrategy, strategyType, n);
        decorator.exec();
        BenchmarkResult result = decorator.getResult();

        System.out.printf("%-5d | %-15s | %-10d | %-15d | %-10d%n", 
            result.n(), result.strategyName(), result.steps(), result.timeInNano(), result.memoryUsedInBytes());
    }
}
