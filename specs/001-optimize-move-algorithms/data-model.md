# 資料模型：演算法優化與基準測試

## 1. 核心實體

### `BenchmarkResult` (基準測試結果)
- `strategyName`: 策略名稱 (String)
- `n`: 問題規模 (int)
- `executionTimeNs`: 執行時間 (long, 奈秒)
- `stepsTaken`: 移動步數 (int)
- `isSuccess`: 是否成功解決 (boolean)

### `State` (搜尋演算法狀態)
- `nodeLayout`: 當前棋盤的序列化字串或列表 (List<Node>)
- `parent`: 上一個狀態 (State)
- `move`: 從上一個狀態到當前狀態所做的移動 (Step)
- `gScore`: 從起始狀態到當前的實際成本 (int)
- `hScore`: 從當前到目標狀態的估計成本 (int, 啟發函數)
- `fScore`: 總估計成本 (int, g + h)

## 2. 關係圖 (概念)
- `Strategy` (介面) <-- `TheBestStrategy` (實作)
- `Strategy` (介面) <-- `BFSStrategy` (新實作)
- `Strategy` (介面) <-- `AStarStrategy` (新實作)
- `Strategy` <-- `BenchmarkDecorator` (包裹任意 Strategy)

## 3. 驗證規則
- 所有 `Strategy` 的 `exec()` 回傳值必須符合 `N * (N + 2)`。
- `State` 比較應基於 `nodeLayout` 的唯一性，以避免死循環。
