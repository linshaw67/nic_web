package com.importadorabacco.web.model.domain;

import com.importadorabacco.web.model.BaseData;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-16.
 */
public class ProductsByCat extends BaseData {
    private Integer catId;
    private String catName;
    private List<ProductInfo> products;

    public ProductsByCat() {
    }

    public ProductsByCat(Integer catId, String catName,
            List<ProductInfo> products) {
        this.catId = catId;
        this.catName = catName;
        this.products = products;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public List<ProductInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfo> products) {
        this.products = products;
    }
}
