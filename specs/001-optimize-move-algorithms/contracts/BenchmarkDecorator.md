# 介面合約：BenchmarkDecorator

## 目的
使用裝飾者模式 (Decorator Pattern) 為 `Strategy` 提供基準測試功能，而不修改原始演算法。

## 行為
- **初始化**: 接收一個 `Strategy` 實例。
- **計時**: 在呼叫 `delegate.exec()` 前後記錄 `System.nanoTime()`。
- **報告**: 執行結束後，列印執行時間、步數與平均每步耗時。
- **透明性**: 對外部呼叫者而言，它仍然是一個 `Strategy`。
