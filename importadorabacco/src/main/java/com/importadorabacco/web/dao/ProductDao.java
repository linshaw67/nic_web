package com.importadorabacco.web.dao;

import com.importadorabacco.web.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-14.
 */
@Repository
public interface ProductDao {
    List<Product> selectAllProducts();
}
