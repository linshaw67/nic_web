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
    List<UserCart> selectByUserId(Long userId);

    int insert(UserCart userCart);

    int delete(@Param("userId") Long userId, @Param("productId") Long productId);
}
