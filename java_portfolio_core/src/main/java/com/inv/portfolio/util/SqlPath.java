package com.inv.portfolio.util;

/**
 * 集中管理系統庫內所有獨立 .sql 檔案的實體 classpath 路徑。
 * 使用 Enum 可確保路徑字串不會被拼錯。
 */
public enum SqlPath {
    
    // Position 相關 SQL
    POSITION_FIND_ALL("sql/position/find_all.sql"),
    POSITION_FIND_BY_SYMBOL("sql/position/find_by_symbol.sql"),
    POSITION_INSERT("sql/position/insert.sql"),
    POSITION_UPDATE("sql/position/update.sql"),

    // Transaction 相關 SQL
    TRANSACTION_INSERT("sql/transaction/insert.sql");

    private final String path;

    SqlPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
