package com.inv.portfolio.model;

import com.inv.portfolio.enums.TradeType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 表示庫存交易明細的實體模型。
 * 用於不可竄改地記錄每一筆出入金、買賣或配息的事件流水帳。
 *
 * @param id              資料庫唯一流水號
 * @param positionId      關聯的特定股票部位 ID (Reference to Position)
 * @param type            交易類型 (例如: BUY, SELL, DIVIDEND, DEPOSIT)
 * @param shares          因該交易而異動的股數 (買賣時)
 * @param price           該筆交易的單股價格
 * @param exchangeRate    該筆交易發生時的匯率 (當地貨幣對美元)
 * @param taxAmount       該筆交易所產生的稅款 (例如 30% 股息預扣稅)
 * @param transactionDate 交易發生的實際時間點
 */
public record Transaction(
        Long id,
        Long positionId,
        TradeType type,
        BigDecimal shares,
        BigDecimal price,
        BigDecimal exchangeRate,
        BigDecimal taxAmount,
        LocalDateTime transactionDate) {
}
