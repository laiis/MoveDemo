package oo.cc.strategies.benchmark;

/**
 * 用於測量執行時間與記憶體消耗的工具類別。
 */
public class Profiler {
    private long startTime;
    private long startMemory;

    public void start() {
        System.gc(); // 盡量清理以減少雜訊
        Runtime runtime = Runtime.getRuntime();
        startTime = System.nanoTime();
        startMemory = runtime.totalMemory() - runtime.freeMemory();
    }

    public long getElapsedTimeNano() {
        return System.nanoTime() - startTime;
    }

    public long getMemoryUsedBytes() {
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory()) - startMemory;
    }
}
