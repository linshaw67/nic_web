package com.importadorabacco.web.dao;

import com.importadorabacco.web.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by tanhengyi on 15-5-14.
 */
@Repository
public interface ProductDao {
    Product selectById(Long id);

    List<Product> select(Map<String, Object> map);

    List<Integer> selectAllCatIds();
}
