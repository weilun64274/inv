package com.inv.portfolio.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Position(
    Long id,
    String symbol,
    BigDecimal shares,
    BigDecimal averageCost,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
