package com.importadorabacco.web.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.importadorabacco.web.model.Product;
import com.importadorabacco.web.model.domain.ProductInfo;
import com.importadorabacco.web.service.BaseService;
import com.importadorabacco.web.service.ProductService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-14.
 */
@Service
public class ProductServiceImpl extends BaseService implements ProductService {
    @Override
    public List<ProductInfo> getProductsByCatId(Integer catId) {
        List<Product> products = productDao.select(ImmutableMap.<String, Object>of("catId", catId));
        if (CollectionUtils.isEmpty(products)) {
            return Lists.newArrayList();
        }

        return Lists.transform(products, new Function<Product, ProductInfo>() {
            @Override
            public ProductInfo apply(Product product) {
                return new ProductInfo(product);
            }
        });
    }

    @Override
    public List<ProductInfo> getAllProducts() {
        return getProductsByCatId(null);
    }

    @Override
    public List<Integer> getAllCatIds() {
        return productDao.selectAllCatIds();
    }
}
