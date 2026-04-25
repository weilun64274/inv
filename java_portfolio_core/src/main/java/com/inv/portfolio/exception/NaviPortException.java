package com.inv.portfolio.exception;

public class NaviPortException extends RuntimeException {

    private final NaviPortErrorCode errorCode;

    public NaviPortException(NaviPortErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public NaviPortException(NaviPortErrorCode errorCode, String additionalDetails) {
        super(errorCode.getMessage() + " - details: " + additionalDetails);
        this.errorCode = errorCode;
    }

    public NaviPortErrorCode getErrorCode() {
        return errorCode;
    }
}
