# 快速入門：演算法優化與基準測試

## 1. 執行基準測試
您可以透過 `Main` 類別選擇要測試的演算法與問題規模 N。

```bash
# 使用 Gradle 執行專案
./gradlew run
```

## 2. 撰寫新的策略 (TDD)
1. 在 `src/test/java/oo/cc/strategies/` 下建立新的測試類別。
2. 定義預期的求解結果：
   ```java
   @Test
   void testNewStrategy() {
       Strategy s = new MyNewStrategy(initialNodes(3));
       assertEquals(15, s.exec()); // N * (N + 2) = 3 * 5 = 15
   }
   ```
3. 在 `src/main/java/oo/cc/strategies/` 下實作 `Strategy` 介面。

## 3. 測量效能
使用 `BenchmarkDecorator` 包裹您的策略：
```java
Strategy optimized = new BenchmarkDecorator(new TheBestStrategy(nodes));
optimized.exec();
```
這將在控制台輸出執行時間與記憶體使用概況。
