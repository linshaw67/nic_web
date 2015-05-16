package com.importadorabacco.web.model.domain;

import com.importadorabacco.web.model.BaseData;
import com.importadorabacco.web.model.Order;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-14.
 */
public class OrderInfo extends BaseData {
    private Order order;
    private List<ProductInfo> products;

    public OrderInfo() {
    }

    public OrderInfo(Order order, List<ProductInfo> products) {
        this.order = order;
        this.products = products;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<ProductInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfo> products) {
        this.products = products;
    }
}
