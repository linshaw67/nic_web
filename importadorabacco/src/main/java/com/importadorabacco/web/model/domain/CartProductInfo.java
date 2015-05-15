package com.importadorabacco.web.model.domain;

import com.importadorabacco.web.model.Product;

/**
 * Created by tanhengyi on 15-5-16.
 */
public class CartProductInfo extends ProductInfo {
    private Integer quantity;

    public CartProductInfo() {
    }

    public CartProductInfo(Product product, Integer quantity) {
        super(product);
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
