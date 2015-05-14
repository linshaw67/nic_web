package com.importadorabacco.web.service;

import com.importadorabacco.web.model.domain.OrderInfo;

/**
 * Created by tanhengyi on 15-5-13.
 */
public interface OrderService {
    /**
     * commit order from user cart
     *
     * @param userId userId
     * @return order info
     */
    OrderInfo commitOrder(Long userId);
}
