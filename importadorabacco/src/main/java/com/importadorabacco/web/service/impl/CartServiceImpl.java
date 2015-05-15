package com.importadorabacco.web.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.importadorabacco.web.model.Product;
import com.importadorabacco.web.model.UserCart;
import com.importadorabacco.web.model.domain.ProductInfo;
import com.importadorabacco.web.service.BaseService;
import com.importadorabacco.web.service.CartService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Service
public class CartServiceImpl extends BaseService implements CartService {
    @Override
    public List<ProductInfo> query(Long userId) {
        Preconditions.checkNotNull(userId);
        List<UserCart> products  = userCartDao.selectByUserId(userId);
        if (CollectionUtils.isEmpty(products)) {
            return Lists.newArrayList();
        }

        return Lists.transform(products, new Function<UserCart, ProductInfo>() {
            @Override
            public ProductInfo apply(UserCart userCart) {
                Product product = productDao.selectById(userCart.getProductId());
                return new ProductInfo(product);
            }
        });
    }

    @Override
    public boolean add(UserCart userCart) {
        Preconditions.checkNotNull(userCart);
        userCartDao.insert(userCart);
        return true;
    }

    @Override
    public boolean remove(UserCart userCart) {
        Preconditions.checkNotNull(userCart);
        userCartDao.delete(userCart.getUserId(), userCart.getProductId());
        return true;
    }
}
