package com.inv.portfolio.application;

import com.inv.portfolio.dto.PositionSummary;
import com.inv.portfolio.dto.TradeRequest;
import com.inv.portfolio.model.Position;
import com.inv.portfolio.service.TradeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 應用層服務 (Application Service / Facade)。
 * 專門給 Controller 直接引用的外觀層，負責匯集和協調底層的 Domain Service 完成複雜場景。
 * 達到了隔離 Web API 層與底層商業邏輯的分層目的。
 */
@Service
public class PortfolioAppService {

    private final TradeService tradeService;

    /**
     * 建構子注入外部服務。
     * 
     * @param tradeService 負責交易邏輯與部位計算的核心服務
     */
    public PortfolioAppService(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * 讀取所有的現存部位列表。
     * 
     * @return 所有的部位紀錄
     */
    public List<Position> getAllPositions() {
        return tradeService.getAllPositions();
    }

    /**
     * 接收並執行一次買進或賣出的交易請求。
     * 
     * @param request 交易細節資料
     */
    public void executeTrade(TradeRequest request) {
        tradeService.processTrade(request);
    }

    /**
     * 取回單一股票統整過後的帳面加權平均屬性。
     * 
     * @param symbol 網址傳遞的股票代碼
     * @return 計算後的整併資訊物件
     */
    public PositionSummary getSymbolSummary(String symbol) {
        return tradeService.calculateSymbolSummary(symbol);
    }
}
