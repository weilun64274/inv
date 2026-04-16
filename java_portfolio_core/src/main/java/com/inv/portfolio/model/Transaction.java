package com.inv.portfolio.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
    Long id,
    Long positionId,
    String type,
    BigDecimal shares,
    BigDecimal price,
    BigDecimal exchangeRate,
    BigDecimal taxAmount,
    LocalDateTime transactionDate
) {}
