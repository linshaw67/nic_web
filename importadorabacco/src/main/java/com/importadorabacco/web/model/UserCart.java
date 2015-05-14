package com.importadorabacco.web.model;

import java.sql.Timestamp;

/**
 * Created by tanhengyi on 15-5-13.
 */
public class UserCart extends BaseData {
    private Long id;
    private Long userId;
    private Long productId;

    public UserCart() {
    }

    public UserCart(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
