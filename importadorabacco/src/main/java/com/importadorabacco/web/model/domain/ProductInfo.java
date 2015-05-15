package com.importadorabacco.web.model.domain;

import com.importadorabacco.web.model.BaseData;
import com.importadorabacco.web.model.Product;

import java.math.BigDecimal;

/**
 * Created by tanhengyi on 15-5-14.
 */
public class ProductInfo extends BaseData {
    private Long productId;
    private String productName;
    private String catId;
    private String catName;
    private BigDecimal price;
    private String desc;
    private String imageUrl;

    public ProductInfo() {
    }

    public ProductInfo(Long productId, String productName, String catId, String catName, String desc, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.catId = catId;
        this.catName = catName;
        this.desc = desc;
        this.imageUrl = imageUrl;
    }

    public ProductInfo(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.catId = product.getCatId();
        this.catName = product.getCatName();
        this.price = product.getPrice();
        this.desc = product.getDesc();
        this.imageUrl = product.getImageUrl();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
