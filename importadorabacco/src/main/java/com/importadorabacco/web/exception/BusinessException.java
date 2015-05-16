package com.importadorabacco.web.exception;

/**
 * Created by tanhengyi on 15-5-16.
 */
public class BusinessException extends RuntimeException {
    private int code;

    public BusinessException(int code) {
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
