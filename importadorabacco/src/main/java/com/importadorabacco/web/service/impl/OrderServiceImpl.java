package com.importadorabacco.web.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.importadorabacco.web.exception.OrderException;
import com.importadorabacco.web.model.*;
import com.importadorabacco.web.model.domain.CommitOrderReq;
import com.importadorabacco.web.model.domain.OrderInfo;
import com.importadorabacco.web.model.domain.ProductInfo;
import com.importadorabacco.web.service.BaseService;
import com.importadorabacco.web.service.OrderService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Service
public class OrderServiceImpl extends BaseService implements OrderService {

    @Transactional
    @Override
    public OrderInfo commitOrder(CommitOrderReq commitOrderReq) {
        logger.info("op=commitOrder, commitOrderReq={}", commitOrderReq);
        Preconditions.checkNotNull(commitOrderReq);

        List<UserCart> userCarts = userCartDao.select(new UserCart(commitOrderReq.getUserId(), null, null));
        if (CollectionUtils.isEmpty(userCarts)) {
            throw new OrderException(1, "Your cart is empty");
        }

        Order order = Order.from(commitOrderReq);
        orderDao.insert(order);

        List<ProductInfo> productInfos = Lists.newArrayList();
        for (UserCart userCart : userCarts) {
            orderCartDao.insert(new OrderCart(order.getId(), userCart.getId(), userCart.getQuantity()));
            Product product = productDao.selectById(userCart.getProductId());
            productInfos.add(new ProductInfo(product));
        }

        OrderInfo orderInfo = new OrderInfo(order, productInfos);
        logger.info("op=commitOrder success, commitOrderReq={}, orderInfo={}", commitOrderReq, orderInfo);

        return orderInfo;
    }
}
