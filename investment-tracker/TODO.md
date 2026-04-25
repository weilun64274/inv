# TODO

> **內部專案名稱**: investment-tracker
> **對外產品名稱**: NaviPort

## 技巧：使用 Git Worktree 讓 AI 同時處理多個任務 (真平行處理)

當遇到需要同時處理多個耗時任務（例如：爬資料 + 解 Bug）時，依序執行以下步驟：

1. **建立新的 Worktree：**
   在終端機輸入 `git worktree add ../<新資料夾名稱> <分支名稱>`
   *(這會將專案的其他分支開在另一個獨立資料夾中)*

2. **開啟新視窗：**
   在 VS Code 等編輯器中開啟剛剛建立的 `<新資料夾名稱>`。

3. **雙開 AI 對話：**
   - **原視窗：** 開啟對話，請 AI 在原有資料夾處理任務一。
   - **新視窗：** 開啟**新對話**，請 AI 在新資料夾處理任務二。

這樣兩個 AI 對話就能真正同時為您工作，且互不干擾。

---

**待辦基礎建設 (Infrastructure Pending):**
- [ ] 建立共用基礎連線環境 (撰寫 `docker-compose.yml` 啟動 PostgreSQL 與 Message Queue)
- [ ] 撰寫跨語言專案啟動與管理腳本 (例如 Makefile 或啟動腳本，一鍵啟動所有服務與環境)

---

## 下次處理 (Next Actions)

### 1. 系統功能盤點

#### 1-1 架構類 (Architecture & Infrastructure)
* **目前已完成架構與模組**：
  * **專案底層架構**：Java (Spring Boot) 與 Python 雙專案分離。
  * **核心持久層**：完成 `Position` 與 `Transaction` 資料庫實體與 JDBC Repository（包含 SQL 獨立檔案與 `SqlPath` 強型別管理）。
  * **業務邏輯處理**：實作帶有動態合併容忍度 (`PortfolioProperties`) 的買進與賣出邏輯 (`TradeService`)。
  * **例外處理架構**：定義了業務例外 (`NaviPortException`) 與系統例外 (`NaviPortSysException`) 及其共用錯誤代碼 Enum。
* **預期還缺哪些架構**：
  * **DB 測試環境**：需啟動 PostgreSQL 確保 DB Schema 與連線正常。
  * **全域錯誤處理**：尚未實作 `@RestControllerAdvice` 來將自訂 Exception 轉換為標準 JSON 回應。
  * **Python 端整合**：市場報價抓取模組尚未動工。

#### 1-2 API 盤點 (API Inventory)
* **目前已具備的 API**：
  * `POST /api/portfolio/trade`：接收前端買進/賣出交易請求，並處理部位均價計算與流水帳紀錄。
  * `GET /api/portfolio/{symbol}/summary`：提供單一股票（Symbol）的庫存統整資訊（總股數、平均成本）。
* **預期還缺哪些 API**：
  * `GET /api/portfolio`：取得目前所有的持有股票清單 (All Positions)。
  * `GET /api/portfolio/transactions`：查詢歷史交易明細流水帳（未來可能需支援分頁與時間區間篩選）。
  * `GET /api/market-data/{symbol}` (預期交由 Python 端提供)：即時獲取美股最新報價，以配合 Java 端資料計算未實現損益。

### 2. 程式碼審查 (Code Review)
* ⚠️ **提醒**：目前的程式碼（尤其是今天重構的 Repository、Exceptions 與 Service 邏輯）尚未經過完整的 Code Review，下次動工前需優先把 code review 完。

### 3. 架構補充：開 Filter
* ⚠️ **提醒我要開 Filter**：下次記得要實作 Filter（或 Interceptor / AOP），可用於處理 API 請求日誌 (Request/Response logging)、CORS 或是其他全域請求攔截機制。

### 4. 全域例外處理 (Global Exception Handler)
* ⚠️ **實作 Exception Handler**：使用 `@RestControllerAdvice` 來攔截 `NaviPortException`、`NaviPortSysException` 與所有未預期的系統 `Exception`，確保前端永遠只會收到標準化格式的 JSON 錯誤回應 (Error Response)。
### 5. 看一下comment.md
* ⚠️ **想要看他的建議是什麽意思**：看懂他的意思，想一下要不要用
