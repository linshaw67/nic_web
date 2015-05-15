package com.importadorabacco.web.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.importadorabacco.web.model.Product;
import com.importadorabacco.web.model.domain.ProductInfo;
import com.importadorabacco.web.service.BaseService;
import com.importadorabacco.web.service.ProductService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by tanhengyi on 15-5-14.
 */
@Service
public class ProductServiceImpl extends BaseService implements ProductService {
    @Override
    public List<ProductInfo> getProductsByCatId(Integer catId) {
        Map<String, Object> paraMap = Maps.newHashMap();
        paraMap.put("catId", catId);
        List<Product> products = productDao.select(paraMap);
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
