package com.importadorabacco.web.model.domain;

import com.importadorabacco.web.model.BaseData;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-14.
 */
public class OrderInfo extends BaseData {
    private Long orderId;
    private String email;
    private List<ProductInfo> products;

    public OrderInfo() {
    }

    public OrderInfo(Long orderId, String email, List<ProductInfo> products) {
        this.orderId = orderId;
        this.email = email;
        this.products = products;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ProductInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfo> products) {
        this.products = products;
    }
}
