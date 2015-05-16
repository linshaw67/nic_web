package com.importadorabacco.web.model;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

/**
 * Created by tanhengyi on 15-5-13.
 */
public class User extends BaseData {
    private Long id;
    private String email;
    private String pwd;
    private Integer status = 0;
    private String token;
    private Timestamp tokenExpireTime;
    private Timestamp createTime;
    private Timestamp lastLoginTime;

    public User() {
    }

    public User(String email, String pwd, String token, Timestamp tokenExprieTime) {
        this.email = email;
        this.pwd = pwd;
        this.token = token;
        this.status = 0;
        this.tokenExpireTime = tokenExprieTime;
    }

    public boolean isActive() {
        return this.status != 0;
    }

    public void active() {
        this.status = 1;
    }

    public boolean isTokenValid(String token) {
        return !StringUtils.isBlank(token)
                && token.equals(this.token)
                && tokenExpireTime.getTime() < System.currentTimeMillis();
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
