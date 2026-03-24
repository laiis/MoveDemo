# 實作計畫：優化移動演算法並探索替代實作

**分支**: `001-optimize-move-algorithms` | **日期**: 2026-03-24 | **規格**: [spec.md](spec.md)
**輸入**: 來自 `/specs/001-optimize-move-algorithms/spec.md` 的功能規格

## 摘要

優化現有的 `TheBestStrategy` 並開發新的求解演算法（如 A*、BFS），以提高處理大規模 N (最高至 26) 的效能。我們將採用 **TDD 開發模式**，嚴格遵守 **SOLID 原則** 並套用 **Java 設計模式**（如 Decorator、Strategy、Factory），目標是達成 **100% 測試覆蓋率** 並產出客觀的基準測試報告。

## Technical Context

**Language/Version**: Java 17  
**Primary Dependencies**: JUnit 5 (Jupiter), Gradle  
**Storage**: N/A  
**Testing**: JUnit 5 (100% 覆蓋率目標)  
**Target Platform**: JVM (CLI)  
**Project Type**: CLI 工具 / 演算法庫  
**Performance Goals**: N=26 時，求解時間需縮短 15% 以上，且在 5 秒內完成。  
**Constraints**: 嚴格遵守 `N * (N + 2)` 最小步數公式；需提供基準測試計時器。  
**Scale/Scope**: 演算法核心邏輯與基準測試框架。

## 憲章檢查 (Constitution Check)

*關卡：必須在 Phase 0 研究前通過。在 Phase 1 設計後重新檢查。*

- [x] **物件導向完整性**: 使用清晰的抽象化 (Node, Space, Strategy)？ 是。
- [x] **驗證驅動**: 必須通過 JUnit 測試並符合步數公式？ 是。
- [x] **CLI 優先的清晰度**: 提供易讀的移動序列輸出？ 是。
- [x] **效能擴展性**: 優化至 N=26 並避免 OOM？ 是。
- [x] **模板同步化**: 遵循 .specify 基礎設施？ 是。
- [x] **正體中文**: 使用正體中文編寫與回答？ 是。

## 專案結構

### 文件 (此特徵)

```text
specs/001-optimize-move-algorithms/
├── plan.md              # 此檔案
├── research.md          # 演算法研究與決策
├── data-model.md        # 基準測試實體與搜尋狀態模型
├── quickstart.md        # 基準測試執行指南
├── contracts/           # Strategy 介面與 Decorator 定義
└── tasks.md             # 任務列表 (後續產生)
```

### 原始碼 (專案根目錄)

```text
src/main/java/oo/cc/
├── nodes/               # 現有節點模型
├── steps/               # 現有移動邏輯
├── strategies/
│   ├── Strategy.java    # 核心介面
│   ├── base/            # 基礎實作或抽象類別
│   ├── exhaustive/      # 現有的窮舉策略
│   ├── thebest/         # 待優化的策略
│   ├── search/          # 新的搜尋演算法 (BFS, A*)
│   └── benchmark/       # BenchmarkDecorator 與計時器
└── Main.java            # 入口點 (增加基準測試選單)

src/test/java/oo/cc/
└── strategies/          # TDD 測試案例
```

**結構決策**: 採用單一專案結構 (Single project)，將新的演算法模組化放置於 `strategies/search` 下，並引入 `benchmark` 子包處理裝飾者模式。

## 複雜性追蹤

> **僅在憲章檢查有違規且須辯護時填寫**

| 違規 | 為什麼需要 | 拒絕簡單替代方案的原因 |
|-----------|------------|-------------------------------------|
| 無 | N/A | N/A |
