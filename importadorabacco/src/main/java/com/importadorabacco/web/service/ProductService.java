package com.importadorabacco.web.service;

import com.importadorabacco.web.model.domain.ProductInfo;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-14.
 */
public interface ProductService {
    List<ProductInfo> getProductsByCatId(Integer catId);

    List<ProductInfo> getAllProducts();

    List<Integer> getAllCatIds();
}
