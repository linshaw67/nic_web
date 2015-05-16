package com.importadorabacco.web.service;

import com.importadorabacco.web.model.domain.CommitOrderReq;
import com.importadorabacco.web.model.domain.OrderInfo;

/**
 * Created by tanhengyi on 15-5-13.
 */
public interface OrderService {
    /**
     * commit order from user cart
     *
     * @param commitOrderReq request order info
     * @return order info
     */
    OrderInfo commitOrder(CommitOrderReq commitOrderReq);
}
