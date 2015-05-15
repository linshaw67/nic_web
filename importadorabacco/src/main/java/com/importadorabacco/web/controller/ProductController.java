package com.importadorabacco.web.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.importadorabacco.web.model.domain.ApiResp;
import com.importadorabacco.web.model.domain.ProductInfo;
import com.importadorabacco.web.model.domain.ProductsByCat;
import com.importadorabacco.web.service.ProductService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tanhengyi on 15-5-15.
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Resource
    private ProductService productService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ApiResp<List<ProductsByCat>> getProducts(@RequestParam("catId") Integer catId) {
        logger.info("op=getProducts, catId={}", catId);

        List<ProductsByCat> productsByCatList = Lists.newArrayList();
        if (catId == null || catId <= 0) {
            List<Integer> catIds = productService.getAllCatIds();
            for (Integer id : catIds) {
                ProductsByCat productsByCat = getProductsByCat(id);
                if (productsByCat != null) {
                    productsByCatList.add(productsByCat);
                }
            }
        } else {
            ProductsByCat productsByCat = getProductsByCat(catId);
            if (productsByCat != null) {
                productsByCatList.add(productsByCat);
            }
        }

        logger.info("op=getProducts, catId={}, ret={}", catId, productsByCatList);
        return new ApiResp<>(productsByCatList);
    }

    private ProductsByCat getProductsByCat(Integer catId) {
        Preconditions.checkNotNull(catId);

        List<ProductInfo> productInfos;
        productInfos = productService.getProductsByCatId(catId);
        if (CollectionUtils.isEmpty(productInfos)) {
            return null;
        }

        return new ProductsByCat(catId, productInfos.get(0).getCatName(), productInfos);
    }

}
