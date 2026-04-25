package com.inv.portfolio.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 表示投資部位的實體模型。
 * 用於記錄某一檔股票在系統中的當下持股狀況與平均成本。
 *
 * @param id          資料庫唯一流水號
 * @param symbol      股票代號 (如 AAPL, MSFT)
 * @param shares      持有的股數
 * @param averageCost 持有該部位的平均取得成本 (每股)
 * @param createdAt   首次建立此部位的時間
 * @param updatedAt   最後一次異動此部位的時間
 */
public record Position(
        Long id,
        String symbol,
        BigDecimal shares,
        BigDecimal averageCost,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
