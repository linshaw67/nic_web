package com.importadorabacco.web.model.domain;

import com.importadorabacco.web.model.BaseData;

/**
 * Created by tanhengyi on 15-5-14.
 */
public class ProductInfo extends BaseData {
    private Long productId;
    private String productName;
    private String desc;
    private String imageUrl;

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
