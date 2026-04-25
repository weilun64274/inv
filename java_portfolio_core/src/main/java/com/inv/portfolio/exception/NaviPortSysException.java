package com.inv.portfolio.exception;

/**
 * 系統級別的不可預期例外（System Exception）。
 * 專門用來處理如資料庫連線中斷、檔案讀取失敗（I/O Error）、第三方 API 不通等非業務層面的重大錯誤。
 */
public class NaviPortSysException extends RuntimeException {

    private final NaviPortErrorCode errorCode;

    public NaviPortSysException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = NaviPortErrorCode.INTERNAL_SERVER_ERROR;
    }

    public NaviPortSysException(NaviPortErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.getMessage() + " - " + message, cause);
        this.errorCode = errorCode;
    }

    public NaviPortErrorCode getErrorCode() {
        return errorCode;
    }
}
