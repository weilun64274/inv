package com.inv.portfolio.repository;

import com.inv.portfolio.model.Transaction;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * 交易流水帳 (Transaction) 專用的資料庫存取庫。
 */
@Repository
public class TransactionRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * 預設建構子。
     *
     * @param jdbcTemplate 供注入的原生 JDBC 操作介面
     */
    public TransactionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 寫入一筆完整的買賣/配息交易明細至系統明細表。
     * 此操作通常會和 Position 的更新包在一起，以確保財務紀錄連動一致性。
     *
     * @param transaction 準備寫入明細庫之物件
     */
    public void insert(Transaction transaction) {
        String sql = """
            INSERT INTO transaction (position_id, type, shares, price, exchange_rate, tax_amount, transaction_date)
            VALUES (:positionId, :type, :shares, :price, :exchangeRate, :taxAmount, :transactionDate)
        """;
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("positionId", transaction.positionId())
            .addValue("type", transaction.type() != null ? transaction.type().name() : null)
            .addValue("shares", transaction.shares())
            .addValue("price", transaction.price())
            .addValue("exchangeRate", transaction.exchangeRate())
            .addValue("taxAmount", transaction.taxAmount())
            .addValue("transactionDate", Timestamp.valueOf(transaction.transactionDate()));
        
        jdbcTemplate.update(sql, params);
    }
}
