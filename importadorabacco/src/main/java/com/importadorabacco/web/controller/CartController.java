package com.importadorabacco.web.controller;

import com.importadorabacco.web.model.UserCart;
import com.importadorabacco.web.model.Order;
import com.importadorabacco.web.model.domain.ApiResp;
import com.importadorabacco.web.model.domain.OrderInfo;
import com.importadorabacco.web.model.domain.ProductInfo;
import com.importadorabacco.web.service.CartService;
import com.importadorabacco.web.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;

    @Resource
    private OrderService orderService;

    /**
     * query user cart
     *
     * @param userId userId
     * @return products in user cart
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ApiResp get(@RequestParam("userId") Long userId) {
        if (userId == null) {
            return ApiResp.failed("param error");
        }

        List<ProductInfo> productInfoList = cartService.query(userId);
        return new ApiResp<>(productInfoList);
    }

    /**
     * add to user cart
     *
     * @param userId userId
     * @param productId productId
     * @return success or not
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp add(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        if (userId == null || productId == null) {
            return ApiResp.failed("param error");
        }

        if (cartService.add(new UserCart(userId, productId))) {
            return ApiResp.success();
        }
        return ApiResp.failed("add to cart failed");
    }

    /**
     * remove from user cart
     *
     * @param userId userId
     * @param productId productId
     * @return success or not
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp remove(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        if (userId == null || productId == null) {
            return ApiResp.failed("param error");
        }

        if (cartService.remove(new UserCart(userId, productId))) {
            return ApiResp.success();
        }
        return ApiResp.failed("remove from cart failed");
    }

    /**
     * commit user cart
     *
     * @param userId userId
     * @return order info
     */
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp commitOrder(@RequestParam("userId") Long userId) {
        if (userId == null) {
            return ApiResp.failed("param error");
        }

        OrderInfo orderInfo = orderService.commitOrder(userId);
        if (orderInfo == null) {
            return ApiResp.failed("commit order failed");
        }

        return new ApiResp<>(orderInfo);
    }
}
