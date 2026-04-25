# 美股分析系統 (US Stock Analysis System) - 開發進度紀錄

## 目前完成階段 (Current Status)
目前系統處於 **「階段一：底層架構初始化與核心財務模組開發」**。

### 🌱 專案基礎建置
- [x] **雙專案結構準備**：拆分為 `python_market_data` (負責爬蟲與量化計算) 與 `java_portfolio_core` (負責資產紀錄與排程)。
- [x] **依賴框架設定**：Java 端使用 Spring Boot 3 + Java 21 + Gradle 建置；Python 端使用 FastAPI + Poetry。
- [x] **分支管理**：成功分離出 `feature/init-project-structure` 並合併至 `main` 主線。目前切換至新開發分支 `feature/trading-logic` 進行業務邏輯開發。

### ☕ Java 資產與財務模組 (`java_portfolio_core`)
- [x] **核心資料結構 (Models & DTOs)**：已建立 `Position` (部位), `Transaction` (交易明細), `TradeRequest` (交易請求), 及 `PositionSummary` (匯總對帳)。
- [x] **資料庫整合 (Repository)**：使用輕量級的 JDBC (`NamedParameterJdbcTemplate`) 實作了 `PositionRepository` 與 `TransactionRepository`，具備基本的寫入與更新能力。
- [x] **核心交易邏輯 (Service)**：在 `PortfolioService` 中實作了 `@Transactional` 的 `recordTrade` 買賣功能。
  - **特色邏輯支援**：實作了「5% 內價格變動視為同部位合併更新平均成本，超出門檻則視為不同部位拆分」的進階買進邏輯。
- [x] **應用程式介面 (Controller)**：
  - `POST /api/portfolio/trade`：接收交易請求。
  - `GET /api/portfolio/{symbol}/summary`：整合回傳單一股票整體的平均成本與持有總股數。
- [x] **程式碼文件化**：為所有的 Class 與 Method 補齊了 JavaDoc 註解說明業務邏輯與參數。(處理中)
- [x] **設定檔與屬性管理重構 (2026-04-25)**：
  - 導入 `@ConfigurationProperties` 建立 `PortfolioProperties` 類別，集中管理 `portfolio.*` 自訂應用程式屬性。
  - 運用巢狀類別 `Trade` 完美對齊 YAML 檔中的階層結構 (`portfolio.trade.merge-tolerance`)。
  - 將 `TradeService` 中寫死的部位合併容忍度常數，改為透過建構子注入 `PortfolioProperties` 讀取，增強了動態調整彈性與編譯期型別安全。
- [x] **例外處理架構與 SQL 管理重構 (2026-04-25)**：
  - **自訂例外系統**：建立 `NaviPortException` (預期內業務例外) 與 `NaviPortSysException` (不可預期系統例外)，並導入 `NaviPortErrorCode` Enum 統一管理全英文錯誤訊息與代碼。
  - **SQL 檔案分離與強型別管理**：將 Repository 中寫死的 SQL 字串抽出為獨立的 `.sql` 檔案（位於 `resources/sql/`），並實作 `SqlPath` Enum 與 `SqlReader` 工具，以強型別方式安全地在應用程式啟動時載入。
- [x] **開發備忘錄與進度盤點 (2026-04-25)**：
  - 更新 `TODO.md`，設立「下次處理 (Next Actions)」備忘區塊。
  - 將系統現有進度清晰歸類為「1-1 架構類」與「1-2 API 盤點」。
  - 增列後續重點工作提醒，包含：程式碼審查 (Code Review)、實作 API 攔截器 (Filter/Interceptor)，以及建立全域例外處理器 (Global Exception Handler)。
