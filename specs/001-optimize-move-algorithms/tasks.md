---

description: "優化移動演算法並探索替代實作的任務清單"
---

# Tasks: 001-optimize-move-algorithms

**Input**: Design documents from `/specs/001-optimize-move-algorithms/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

**Tests**: 採用 TDD 開發模式，所有實作前必須先撰寫測試案例。

## Format: `[ID] [P?] [Story] Description`

- **[P]**: 可並行執行（不同檔案，無未完成依賴）
- **[Story]**: 該任務屬於哪個使用者故事（例如：US1, US2, US3）
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

**Independent Test**: 比較優化版本與基準版本在 N=26 時的執行時間。

### Tests for User Story 2

- [X] T012 [P] [US2] 撰寫針對大規模 N 的效能回歸測試於 src/test/java/oo/cc/PerformanceTest.java

### Implementation for User Story 2

- [X] T013 [US2] 移除 TheBestStrategy.java 中不必要的 I/O 呼叫（如 Step.showList）
- [X] T014 [US2] 優化節點遍歷邏輯以減少對象建立次數於 src/main/java/oo/cc/strategies/thebest/TheBestStrategy.java
- [X] T015 [US2] 執行優化後的測試並記錄 N=26 的數據，確認符合 SC-001
- [ ] T012-1 [US2] 執行測試 src/test/java/oo/cc/PerformanceTest.java， 確認通過測試

**Checkpoint**: 使用者故事 2 已完成，現有策略已獲得顯著優化

---

## Phase 5: User Story 3 - 實作替代求解策略 (Priority: P3)

**Goal**: 實作 BFS 與 A* 搜尋策略，提供多樣化的求解選擇。

**Independent Test**: 新策略必須通過 StrategyTest 驗證且符合公式 N*(N+2)。

### Tests for User Story 3

- [X] T016 [P] [US3] 撰寫 A* 啟發函數的單元測試於 src/test/java/oo/cc/HeuristicTest.java
- [X] T017 [P] [US3] 在 StrategyTest.java 中新增對 BFSStrategy 與 AStarStrategy 的驗證

### Implementation for User Story 3

- [ ] T018 [P] [US3] 實作 BFS 策略於 src/main/java/oo/cc/strategies/search/BFSStrategy.java
- [X] T019 [P] [US3] 實作 A* 策略於 src/main/java/oo/cc/strategies/search/AStarStrategy.java
- [X] T020 [US3] 定義 A* 的啟發函數（Heuristic Function）於 src/main/java/oo/cc/strategies/search/Heuristics.java
- [ ] T021 [US3] 在 Main.java 選單中增加新策略的選擇分支

**Checkpoint**: 所有使用者故事實作完成，具備多種演算法選擇

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: 整體效能驗證、文件更新與程式碼清理

- [ ] T022 [P] 更新 README.md 與 quickstart.md 中的執行範例
- [ ] T023 執行完整測試套件，確保 100% 程式碼覆蓋率
- [ ] T024 [P] 清理與重構重複代碼，確保符合 SOLID 原則
- [ ] T025 產生最終的基準測試比較報告並更新至 research.md

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: 無依賴，立即開始。
- **Foundational (Phase 2)**: 依賴 Phase 1，阻擋所有使用者故事。
- **User Stories (Phase 3+)**: 依賴 Phase 2。可依優先順序 (P1 → P2 → P3) 進行。
- **Polish (Final Phase)**: 依賴所有使用者故事完成。

### Parallel Opportunities

- T002, T005, T006 可並行執行。
- 測試任務 (T007, T012, T016, T017) 可與對應實作並行（或遵循 TDD 先行）。
- BFS (T018) 與 A* (T019) 實作可並行執行。

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. 完成 Phase 1 & 2。
2. 完成 Phase 3 (基準測試框架)。
3. **驗證**: 確保能輸出 N=26 的現有數據。

### Incremental Delivery

1. Foundation ready → MVP (US1) → 優化版本 (US2) → 多樣化演算法 (US3) → Final Polish。
