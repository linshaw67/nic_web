package com.importadorabacco.web.service.impl;

import com.importadorabacco.web.model.domain.ProductInfo;
import com.importadorabacco.web.service.BaseService;
import com.importadorabacco.web.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tanhengyi on 15-5-14.
 */
@Service
public class ProductServiceImpl extends BaseService implements ProductService {
    @Override
    public List<ProductInfo> getAllProducts() {
        return null;
    }
}
