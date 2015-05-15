package com.importadorabacco.web.dao;

import com.importadorabacco.web.model.UserCart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Repository
public interface UserCartDao {
    List<UserCart> select(UserCart userCart);

    int update(UserCart userCart);

    int insert(UserCart userCart);

    int deleteOneInCart(@Param("userId") Long userId, @Param("productId") Long productId);

    int deleteByUserId(Long userId);
}
