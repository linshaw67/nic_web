package com.importadorabacco.web.model;

import java.sql.Timestamp;

/**
 * Created by tanhengyi on 15-5-13.
 */
public class Order extends BaseData {
    private Long id;
    private Long userId;
    private Timestamp createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
