package oo.cc.strategies.benchmark;

/**
 * 儲存基準測試結果的資料模型。
 */
public record BenchmarkResult(
    String strategyName,
    int n,
    int steps,
    long timeInNano,
    long memoryUsedInBytes
) {
    public double timeInMillis() {
        return timeInNano / 1_000_000.0;
    }

    public double memoryInMB() {
        return memoryUsedInBytes / (1024.0 * 1024.0);
    }
}
