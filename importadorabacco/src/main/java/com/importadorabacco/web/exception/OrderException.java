package com.importadorabacco.web.exception;

/**
 * Created by tanhengyi on 15-5-16.
 */
public class OrderException extends RuntimeException {
    private int code;

    public OrderException(int code) {
        this.code = code;
    }

    public OrderException(String message) {
        super(message);
    }

    public OrderException(int code, String message) {
        super(message);
        this.code = code;
    }

    public OrderException(int code, String message, Throwable cause) {
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
