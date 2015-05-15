package com.importadorabacco.web.service.impl;

import com.google.common.collect.Lists;
import com.importadorabacco.web.model.*;
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

    @Override
    public OrderInfo commitOrder(Long userId) {
        List<UserCart> userCarts = userCartDao.selectByUserId(userId);
        if (CollectionUtils.isEmpty(userCarts)) {
            return null;
        }

        OrderInfo orderInfo = commitOrder(userId, userCarts);
        if (orderInfo == null) {
            return null;
        }

        sendEmail(orderInfo);
        return orderInfo;
    }

    @Transactional
    public OrderInfo commitOrder(Long userId, List<UserCart> userCarts) {
        Order order = new Order(userId);
        orderDao.insert(order);

        List<ProductInfo> productInfos = Lists.newArrayList();
        for (UserCart userCart : userCarts) {
            orderCartDao.insert(new OrderCart(order.getId(), userCart.getId(), userCart.getQuantity()));
            Product product = productDao.selectById(userCart.getProductId());
            productInfos.add(new ProductInfo(product));
        }

        User user = userDao.selectById(userId);

        return new OrderInfo(order.getId(), user.getEmail(), productInfos);
    }

    private void sendEmail(OrderInfo orderInfo) {
        logger.info("sendEmail, orderInfo={}", orderInfo);
    }

}
