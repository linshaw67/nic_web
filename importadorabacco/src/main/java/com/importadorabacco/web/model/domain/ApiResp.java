package com.importadorabacco.web.model.domain;

import com.importadorabacco.web.model.BaseData;

/**
 * Created by tanhengyi on 15-5-14.
 */
public class ApiResp<T> extends BaseData {

    private Integer status;
    private String msg;
    private T data;

    public ApiResp() {
    }

    public ApiResp(T data) {
        this.status = 0;
        this.msg = "ok";
        this.data = data;
    }

    public ApiResp(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static ApiResp success() {
        return new ApiResp<>(0, "ok", null);
    }

    public static ApiResp failed(String msg) {
        return new ApiResp<>(-1, msg, null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
