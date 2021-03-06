package com.importadorabacco.web.controller;

import com.importadorabacco.web.model.domain.ProductInfo;
import com.importadorabacco.web.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Controller
@RequestMapping
public class HomeController {
    @Resource
    private ProductService productService;

    @RequestMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("/html/home");
    }

    @RequestMapping("/product")
    public ModelAndView product() {
        List<ProductInfo> productInfos = productService.getAllProducts();
        return new ModelAndView("/html/product").addObject("productInfos", productInfos);
    }
}
