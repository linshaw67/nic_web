package com.importadorabacco.web.model;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

/**
 * Created by tanhengyi on 15-8-2.
 */
public class ResetToken extends BaseData {
    private Long id;
    private String email;
    private String token;
    private Integer status = 0;
    private Timestamp tokenExpireTime;
    private Timestamp createTime;

    public ResetToken() {
    }

    public ResetToken(String email, String token, Timestamp tokenExpireTime) {
        this.email = email;
        this.token = token;
        this.status = 0;
        this.tokenExpireTime = tokenExpireTime;
    }

    public boolean isUsed() {
        return this.status == 1;
    }

    public void use() {
        this.status = 1;
    }

    public boolean isValid() {
        return !StringUtils.isBlank(token)
                && tokenExpireTime.getTime() > System.currentTimeMillis()
                && status == 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Timestamp tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
