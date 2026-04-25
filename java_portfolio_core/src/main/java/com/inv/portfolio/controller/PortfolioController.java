package com.inv.portfolio.controller;

import com.inv.portfolio.application.PortfolioAppService;
import com.inv.portfolio.dto.PositionSummary;
import com.inv.portfolio.dto.TradeRequest;
import com.inv.portfolio.model.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 掌管投資組合(資產管理)相關的對外 Web API 進入點。
 * 提供路由將外部請求對接到與帳本相關的應用層模組 (Application Service) 之中。
 */
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {
    
    private final PortfolioAppService portfolioAppService;

    /**
     * 控制器的主要建構子。
     * 
     * @param portfolioAppService 提供被調用的應用層外觀大管家。
     */
    public PortfolioController(PortfolioAppService portfolioAppService) {
        this.portfolioAppService = portfolioAppService;
    }

    /**
     * 取得原始所有分離部位的陣列清單 API。
     * 這會回傳統計數據最源頭的原始資料。
     * 
     * @return 部位物件陣列
     */
    @GetMapping("/positions")
    public List<Position> getPositions() {
        return portfolioAppService.getAllPositions();
    }

    /**
     * 接收並執行一次買進或賣出的交易行為，觸發資金增疊與寫入。
     * 
     * @param request 從 HTTP body 接收使用者傳入的交易細項
     * @return HTTP 200 行為 OK
     */
    @PostMapping("/trade")
    public ResponseEntity<Void> recordTrade(@RequestBody TradeRequest request) {
        portfolioAppService.executeTrade(request);
        return ResponseEntity.ok().build();
    }

    /**
     * 單一股票所有不同價位部位的綜合平均試算 API。
     * 無論該股票底下拆分為幾個 position，這個 API 負責幫前端算出此股票最終所抱有的真實平均成本與真實持有總數。
     * 
     * @param symbol 欲查詢的單一股票代碼 (由 Path 解析)
     * @return 結算後單純反映總合加權成本的 DTO (PositionSummary)
     */
    @GetMapping("/{symbol}/summary")
    public ResponseEntity<PositionSummary> getSymbolSummary(@PathVariable String symbol) {
        return ResponseEntity.ok(portfolioAppService.getSymbolSummary(symbol));
    }
}
