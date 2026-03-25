---

description: "優化移動演算法並探索替代實作的任務清單"
---

# Tasks: 001-optimize-move-algorithms

**Input**: Design documents from `/specs/001-optimize-move-algorithms/`, `PieceRule.md`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

**Tests**: 採用 TDD 開發模式，所有實作前必須先撰寫測試案例。

## Format: `[ID] [P?] [Story] Description`

- **[P]**: 可並行執行（不同檔案，無未完成依賴）
- **[Story]**: 該任務屬於哪個使用者故事（例如：US1, US2, US3, US4）
- 描述中包含精確的檔案路徑

---

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: 專案初始化與基礎結構設定

- [X] T001 建立專案目錄結構並確認 Gradle 設定於 build.gradle
- [X] T002 [P] 設定 JUnit 5 測試環境與必要的依賴項目於 build.gradle

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: 核心基礎設施，必須在開始使用者故事前完成

- [X] T003 定義核心介面 Strategy 於 src/main/java/oo/cc/strategies/Strategy.java
- [X] T004 建立 BenchmarkResult 資料模型於 src/main/java/oo/cc/strategies/benchmark/BenchmarkResult.java
- [X] T005 [P] 實作 BenchmarkDecorator 於 src/main/java/oo/cc/strategies/benchmark/BenchmarkDecorator.java
- [X] T006 [P] 建立基礎搜尋狀態模型 State 於 src/main/java/oo/cc/strategies/search/State.java

**Checkpoint**: 基礎設施已就緒 - 使用者故事實作可以開始並行進行

---

## Phase 3: User Story 1 - 基準測試現有演算法 (Priority: P1) 🎯 MVP

**Goal**: 為目前的演算法建立基準效能，客觀衡量改進情況。

**Independent Test**: 針對 N=1 到 N=26 執行 `ExhaustiveStrategy` 與 `TheBestStrategy` 並記錄指標。

### Tests for User Story 1

- [X] T007 [P] [US1] 撰寫現有策略的基準測試案例於 src/test/java/oo/cc/StrategyTest.java

### Implementation for User Story 1

- [X] T008 [US1] 在 Main.java 中整合 BenchmarkDecorator 以支援效能報告輸出
- [X] T009 [US1] 執行基準測試並記錄 N=1 到 N=26 的現有數據於 research.md
- [X] T010 [US1] 實作簡單的計時器與記憶體監控工具於 src/main/java/oo/cc/strategies/benchmark/Profiler.java
- [X] T011 [US1] 驗證基準測試報告格式符合 FR-001 要求

**Checkpoint**: 使用者故事 1 已完成，可獨立進行基準測試

---

## Phase 4: User Story 2 - 優化 TheBestStrategy (Priority: P2)

**Goal**: 精煉 `TheBestStrategy` 邏輯，達成 N=26 效能提升 15%。

**Independent Test**: 比較優化版本與基準版本? N=26 時的執行時間。

### Tests for User Story 2

- [X] T012 [P] [US2] 撰寫針對大規模 N 的效能回歸測試於 src/test/java/oo/cc/PerformanceTest.java

### Implementation for User Story 2

- [X] T013 [US2] 移除 TheBestStrategy.java 中不必要的 I/O 呼叫（如 Step.showList）
- [X] T014 [US2] 優化節點遍歷邏輯以減少對象建立次數於 src/main/java/oo/cc/strategies/thebest/TheBestStrategy.java
- [X] T015 [US2] 執行優化後的測試並記錄 N=26 的數據，確認符合 SC-001
- [X] T012-1 [US2] 執行測試 src/test/java/oo/cc/PerformanceTest.java， 確認通過測試

**Checkpoint**: 使用者故事 2 已完成，現有策略已獲得顯著優化

---

## Phase 5: User Story 3 - 實作替代搜尋策略 (Priority: P3)

**Goal**: 實作 BFS 與 A* 搜尋策略，提供多樣化的求解選擇。

**Independent Test**: 新策略必須通過 StrategyTest 驗證且符合公式 N*(N+2)。

### Tests for User Story 3

- [X] T016 [P] [US3] 撰寫 A* 啟發函數的單元測試於 src/test/java/oo/cc/HeuristicTest.java
- [X] T017 [P] [US3] 在 StrategyTest.java 中新增對 BFSStrategy 與 AStarStrategy 的驗證

### Implementation for User Story 3

- [X] T018 [P] [US3] 實作 BFS 策略於 src/main/java/oo/cc/strategies/search/BFSStrategy.java
- [X] T019 [P] [US3] 實作 A* 策略於 src/main/java/oo/cc/strategies/search/AStarStrategy.java
- [X] T020 [US3] 定義 A* 的啟發函數（Heuristic Function）於 src/main/java/oo/cc/strategies/search/Heuristics.java
- [X] T021 [US3] 在 Main.java 選單中增加新策略的選擇分支

**Checkpoint**: 所有使用者故事實作完成，具備多種搜尋演算法選擇

---

## Phase 6: User Story 4 - 實作規則導向遞迴策略 (Priority: P4)

**Goal**: 根據 PieceRule.md 實作第二個移動演算法（遞迴回溯），嚴格遵循左右輪替規則。

**Independent Test**: 驗證演算法能正確解決遊戲，且移動路徑符合「左右交替」與「單回合一次」規則。

### Tests for User Story 4

- [X] T026 [P] [US4] 撰寫針對 PieceRule 輪替規則的驗證測試於 src/test/java/oo/cc/PieceRuleTest.java
- [ ] T027 [P] [US4] 在 StrategyTest.java 中新增對 RecursionStrategy 的驗證

### Implementation for User Story 4

- [X] T028 [P] [US4] 建立 RecursionStrategy 於 src/main/java/oo/cc/strategies/search/RecursionStrategy.java
- [ ] T029 [US4] 實作基於 PieceRule.md 的狀態移轉邏輯（含滑動與跳躍）
- [ ] T030 [US4] 實作「左右輪替」與「每回合移動一次」的移動限制邏輯
- [ ] T031 [US4] 實作「回退至上上一步」的遞迴回溯機制
- [ ] T032 [US4] 在 Main.java 中整合 RecursionStrategy 至演算法選單

**Checkpoint**: 使用者故事 4 已完成，成功實作第二種基於規則的移動演算法

---

## Phase 7: Polish & Cross-Cutting Concerns

**Purpose**: 整體效能驗證、文件更新與程式碼清理

- [ ] T033 [P] 更新 README.md 與 quickstart.md 中的執行範例（包含新演算法）
- [ ] T034 執行完整測試套件，確保 100% 程式碼覆蓋率
- [ ] T035 [P] 清理與重構重複代碼，確保符合 SOLID 原則
- [ ] T036 產生最終的基準測試比較報告並更新至 research.md

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: 無依賴，立即開始。
- **Foundational (Phase 2)**: 依賴 Phase 1，阻擋所有使用者故事。
- **User Stories (Phase 3+)**: 依賴 Phase 2。
  - US1, US2, US3, US4 可在基礎設施完成後並行進行。
- **Polish (Final Phase)**: 依賴所有使用者故事完成。

### User Story Dependencies

- **US4 (RecursionStrategy)**: 不依賴其他搜尋策略，但需依賴 `PieceRule.md` 中的規則定義。

### Parallel Opportunities

- T026, T027, T028 可並行執行。
- 不同的策略實作（US3 與 US4）可由不同開發者同時進行。

---

## Implementation Strategy

### MVP First (User Story 4 Only)

1. 完成 Phase 1 & 2。
2. 撰寫 `RecursionStrategy` 的基礎測試。
3. 實作最小可行性的遞迴邏輯（僅限 N=1 或 N=2）。
4. **驗證**: 確保移動順序符合 PieceRule.md。

### Incremental Delivery

1. 實作基礎遞迴路徑。
2. 加入左右輪替約束。
3. 加入回溯優化邏輯（回退至上上一步）。
4. 最終整合至基準測試框架進行效能比較。
