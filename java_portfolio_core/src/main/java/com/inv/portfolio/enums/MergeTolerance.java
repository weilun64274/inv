package com.inv.portfolio.enums;

import java.math.BigDecimal;

/**
 * 部位合併的價格容忍度門檻列舉。
 * 用於判定新買入的股票是否應與舊部位合併計算均價，或切分為獨立的新部位。
 */
public enum MergeTolerance {
    /** 嚴格合併標準 (差距 2% 內) */
    STRICT("0.02"),
    
    /** 預設合併標準 (差距 5% 內) */
    DEFAULT("0.05"),
    
    /** 寬鬆合併標準 (差距 10% 內) */
    RELAXED("0.10");

    private final BigDecimal ratio;

    MergeTolerance(String ratioValue) {
        this.ratio = new BigDecimal(ratioValue);
    }

    public BigDecimal getRatio() {
        return ratio;
    }
}
