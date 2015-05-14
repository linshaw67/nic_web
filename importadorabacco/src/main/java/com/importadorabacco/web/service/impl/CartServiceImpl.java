package com.importadorabacco.web.service.impl;

import com.google.common.collect.Lists;
import com.importadorabacco.web.model.UserCart;
import com.importadorabacco.web.model.domain.ProductInfo;
import com.importadorabacco.web.service.BaseService;
import com.importadorabacco.web.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Service
public class CartServiceImpl extends BaseService implements CartService {
    @Override
    public List<ProductInfo> query(Long userId) {
        return Lists.newArrayList();
    }

    @Override
    public boolean add(UserCart userCart) {
        return false;
    }

    @Override
    public boolean remove(UserCart userCart) {
        return false;
    }
}
