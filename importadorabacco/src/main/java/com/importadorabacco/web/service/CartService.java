package com.importadorabacco.web.service;

import com.importadorabacco.web.model.UserCart;
import com.importadorabacco.web.model.domain.ProductInfo;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-13.
 */
public interface CartService {
    /**
     * query products in cart
     *
     * @param userId userId
     * @return product info in user cart
     */
    List<ProductInfo> query(Long userId);

    /**
     * add to cart
     *
     * @param userCart cart info
     * @return true success, false failed
     */
    boolean add(UserCart userCart);

    /**
     * remove from cart
     *
     * @param userCart cart info
     * @return true success, false failed
     */
    boolean remove(UserCart userCart);
}
