package com.inv.portfolio.dto;

import java.math.BigDecimal;

/**
 * 股票部位匯總對帳用的資料傳輸物件 (DTO)。
 * 因為同一檔股票可能拆分為不同價格段的獨立部位，此物件用於回傳整併後的總覽數據。
 *
 * @param symbol      股票代號
 * @param totalShares 持有該股票的總股數
 * @param averageCost 所有不同部位算出的加權平均成本
 */
public record PositionSummary(
    String symbol,
    BigDecimal totalShares,
    BigDecimal averageCost
) {}
