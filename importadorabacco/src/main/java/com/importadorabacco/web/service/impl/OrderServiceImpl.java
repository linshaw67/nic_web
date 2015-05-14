package com.importadorabacco.web.service.impl;

import com.importadorabacco.web.model.domain.OrderInfo;
import com.importadorabacco.web.service.BaseService;
import com.importadorabacco.web.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Service
public class OrderServiceImpl extends BaseService implements OrderService {

    @Override
    public OrderInfo commitOrder(Long userId) {
        return null;
    }
}
