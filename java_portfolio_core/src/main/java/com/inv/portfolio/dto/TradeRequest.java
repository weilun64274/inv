package com.inv.portfolio.dto;

import com.inv.portfolio.enums.TradeType;
import java.math.BigDecimal;

/**
 * 接收自前端的交易請求資料傳輸物件 (DTO)。
 * 用戶發起一筆買進或賣出時會涵蓋這些資料。
 *
 * @param symbol       欲交易的股票代號
 * @param type         交易類型 (例如 BUY, SELL)
 * @param shares       欲交易的股數
 * @param price        該筆交易的成交單價
 * @param exchangeRate 該次交易的即時匯率
 * @param taxAmount    涉及的額外稅款金額
 */
public record TradeRequest(
    String symbol,
    TradeType type,
    BigDecimal shares,
    BigDecimal price,
    BigDecimal exchangeRate,
    BigDecimal taxAmount
) {}
