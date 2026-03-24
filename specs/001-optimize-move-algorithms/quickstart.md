# 快速入門：演算法優化與基準測試

## 1. 執行基準測試
您可以透過 `Main` 類別選擇要測試的演算法與問題規模 N。

```bash
# 使用 Gradle 執行專案 (基準測試包含 TheBest, BFS, AStar)
./gradlew run
```

## 2. 策略選擇
現已支援下列策略的效能比較：
- `TheBest`: 優化啟發式搜尋 (N=1-26)
- `BFS`: 廣度優先搜尋 (N=1-10)
- `AStar`: A* 搜尋演算法 (N=1-10)

## 3. 撰寫新的策略 (TDD)
1. 在 `src/test/java/oo/cc/strategies/` 下建立新的測試類別。
2. 定義預期的求解結果 (最小步數公式：`N * (N + 2)`):
   ```java
   @Test
   void testNewStrategy() {
       Strategy s = new MyNewStrategy(initialNodes(3));
       assertEquals(15, s.exec()); // 3 * (3 + 2) = 15
   }
   ```
3. 在 `src/main/java/oo/cc/strategies/` 下實作 `Strategy` 介面。

## 4. 測量效能
使用 `BenchmarkDecorator` 包裹您的策略：
```java
Strategy optimized = new BenchmarkDecorator(new TheBestStrategy(nodes), "TheBest", 26);
optimized.exec();
```
這將在控制台輸出執行時間與記憶體使用概況。

## Story

Place three white pieces (O) and three black pieces (X) into the grid as shown below:

| O | O | O | space | X | X | X |

The question is: 

**How can we move the three white pieces to the positions occupied by the black pieces?** 

Rules:

1. A piece can be moved to an adjacent empty space.
2. A piece can jump over an adjacent piece and land on an empty space.
3. The formula for the minimum number of steps in a jump game with equal-length moves is as follows: steps = N * (N + 2)

Extended question:

If we replace the white pieces with lowercase English letters and the black pieces with uppercase English letters, please list the number of steps and the moves for all letters from 1 to 26.


ref: <個人移位跳棋遊戲的探討>, https://twsf.ntsec.gov.tw/activity/race-1/41/%B0%EA%A4%A4%B2%D5/%BC%C6%BE%C7%AC%EC/4303.pdf
