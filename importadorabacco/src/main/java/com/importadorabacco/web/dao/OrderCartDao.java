package com.importadorabacco.web.dao;

import com.importadorabacco.web.model.OrderCart;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-14.
 */
@Repository
public interface OrderCartDao {
    int insert(OrderCart orderCart);

    List<OrderCart> selectByOrderId(Long orderid);
}
