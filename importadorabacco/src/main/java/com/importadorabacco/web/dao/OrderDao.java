package com.importadorabacco.web.dao;

import com.importadorabacco.web.model.Order;
import org.springframework.stereotype.Repository;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Repository
public interface OrderDao {
    int insert(Order order);

    Order selectById(Long id);
}
