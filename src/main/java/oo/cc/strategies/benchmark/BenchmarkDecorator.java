package oo.cc.strategies.benchmark;

import oo.cc.strategies.Strategy;

import java.io.PrintStream;

/**
 * Strategy 介面的裝飾者，用於測量效能。
 */
public class BenchmarkDecorator implements Strategy {
    private final Strategy strategy;
    private final String name;
    private final int n;
    private BenchmarkResult result;
    private final Profiler profiler;

    public BenchmarkDecorator(Strategy strategy, String name, int n) {
        this.strategy = strategy;
        this.name = name;
        this.n = n;
        this.profiler = new Profiler();
    }

    @Override
    public int exec() {
        // 先暫時重新導向 System.out 以攔截 I/O 輸出 (優化目標之一)
        PrintStream originalOut = System.out;
        // 在執行基準測試時，我們通常不希望看到大量的 move 序列輸出
        System.setOut(new PrintStream(new java.io.OutputStream() {
            public void write(int b) { /* 靜默 */ }
        }));

        profiler.start();
        int steps = strategy.exec();
        long time = profiler.getElapsedTimeNano();
        long memory = profiler.getMemoryUsedBytes();

        System.setOut(originalOut);

        this.result = new BenchmarkResult(name, n, steps, time, memory);
        return steps;
    }

    public BenchmarkResult getResult() {
        return result;
    }
}
