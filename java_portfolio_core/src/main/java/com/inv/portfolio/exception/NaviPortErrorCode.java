package com.inv.portfolio.exception;

public enum NaviPortErrorCode {

    /** Trade related errors (ERR_TRADE_xxx) */
    UNSUPPORTED_TRADE_TYPE("ERR_TRADE_001", "Unsupported trade type"),
    INSUFFICIENT_SHARES("ERR_TRADE_002", "Insufficient shares in a single position to sell"),

    /** System common errors (ERR_SYS_xxx) */
    INTERNAL_SERVER_ERROR("ERR_SYS_500", "Unknown internal server error"),
    INVALID_REQUEST_PARAM("ERR_SYS_400", "Invalid request parameter");

    private final String code;
    private final String message;

    NaviPortErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
