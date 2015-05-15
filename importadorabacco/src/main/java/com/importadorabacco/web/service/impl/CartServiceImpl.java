package com.importadorabacco.web.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.importadorabacco.web.model.Product;
import com.importadorabacco.web.model.UserCart;
import com.importadorabacco.web.model.domain.CartProductInfo;
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
    public List<CartProductInfo> query(Long userId) {
        logger.info("op=queryCart, userId={}", userId);

        Preconditions.checkNotNull(userId);
        List<UserCart> products = userCartDao.select(new UserCart(userId, null, null));
        if (CollectionUtils.isEmpty(products)) {
            return Lists.newArrayList();
        }

        List<CartProductInfo> cartProductInfos = Lists.transform(products, new Function<UserCart, CartProductInfo>() {
            @Override
            public CartProductInfo apply(UserCart userCart) {
                Product product = productDao.selectById(userCart.getProductId());
                return new CartProductInfo(product, userCart.getQuantity());
            }
        });
        logger.info("op=queryCart, userId={}, cartProductInfos={}", userId, cartProductInfos);

        return cartProductInfos;
    }

    @Override
    public boolean add(UserCart userCart) {
        logger.info("op=addCart, userCart={}", userCart);
        Preconditions.checkNotNull(userCart);

        List<UserCart> userCarts = userCartDao.select(userCart);
        UserCart curUserCart;
        if (CollectionUtils.isEmpty(userCarts)) {
            userCartDao.insert(userCart);
            curUserCart = userCart;
        } else {
            curUserCart = userCarts.get(0);
            curUserCart.addQuantity(userCart.getQuantity());
            userCartDao.update(curUserCart);
        }
        logger.info("op=addCart ret={}, userCart={}", curUserCart, userCart);

        return true;
    }

    @Override
    public boolean remove(UserCart userCart) {
        logger.info("op=removeCart, userCart={}", userCart);
        Preconditions.checkNotNull(userCart);

        List<UserCart> userCarts = userCartDao.select(userCart);
        if (CollectionUtils.isEmpty(userCarts)) {
            return true;
        }
        UserCart curUserCart = userCarts.get(0);
        curUserCart.addQuantity(-userCart.getQuantity());
        if (curUserCart.getQuantity() > 0) {
            userCartDao.update(curUserCart);
        } else {
            userCartDao.deleteOneInCart(userCart.getUserId(), userCart.getProductId());
        }
        logger.info("op=removeCart ret={}, userCart={}", curUserCart, userCart);

        return true;
    }

    @Override
    public boolean clear(Long userId) {
        logger.info("op=clearCart, userId={}", userId);
        Preconditions.checkNotNull(userId);
        userCartDao.deleteByUserId(userId);

        return true;
    }
}
