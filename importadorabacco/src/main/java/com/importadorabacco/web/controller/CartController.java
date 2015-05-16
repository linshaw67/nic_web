package com.importadorabacco.web.controller;

import com.importadorabacco.web.exception.OrderException;
import com.importadorabacco.web.model.Order;
import com.importadorabacco.web.model.UserCart;
import com.importadorabacco.web.model.domain.ApiResp;
import com.importadorabacco.web.model.domain.CartProductInfo;
import com.importadorabacco.web.model.domain.CommitOrderReq;
import com.importadorabacco.web.model.domain.OrderInfo;
import com.importadorabacco.web.service.CartService;
import com.importadorabacco.web.service.EmailService;
import com.importadorabacco.web.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private EmailService emailService;

    /**
     * query user cart
     *
     * @param userId userId
     * @return products in user cart
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ApiResp get(@RequestParam("userId") Long userId) {
        if (userId == null) {
            return ApiResp.failed("param error");
        }

        List<CartProductInfo> productInfoList = cartService.query(userId);
        return new ApiResp<>(productInfoList);
    }

    /**
     * add to user cart
     *
     * @param userId    userId
     * @param productId productId
     * @return success or not
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp add(@RequestParam("userId") Long userId,
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity) {
        if (userId == null || productId == null || quantity == null || quantity <= 0) {
            return ApiResp.failed("param error");
        }

        if (cartService.add(new UserCart(userId, productId, quantity))) {
            return ApiResp.success();
        }
        return ApiResp.failed("add to cart failed");
    }

    /**
     * remove from user cart
     *
     * @param userId    userId
     * @param productId productId
     * @return success or not
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp remove(@RequestParam("userId") Long userId,
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity) {
        if (userId == null || productId == null || quantity == null || quantity <= 0) {
            return ApiResp.failed("param error");
        }

        if (cartService.remove(new UserCart(userId, productId, quantity))) {
            return ApiResp.success();
        }
        return ApiResp.failed("remove from cart failed");
    }

    /**
     * commit user cart
     *
     * @param commitOrderReq request order info
     * @return order info
     */
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp commitOrder(@RequestBody CommitOrderReq commitOrderReq) {
        // commit order
        OrderInfo orderInfo;
        try {
            checkCommitOrderReq(commitOrderReq);
            orderInfo = orderService.commitOrder(commitOrderReq);
        } catch (OrderException e) {
            return new ApiResp<>(e.getCode(), e.getMessage(), "");
        }

        // clear cart
        cartService.clear(commitOrderReq.getUserId());

        // send email
        emailService.sendOrderEmail(orderInfo);

        return new ApiResp<>(orderInfo);
    }

    private void checkCommitOrderReq(CommitOrderReq commitOrderReq) {
        if (commitOrderReq == null) {
            throw new OrderException(-1, "order info can not be empty");
        }
        if (commitOrderReq.getUserId() == null) {
            throw new OrderException(-2, "need login first");
        }
        if (StringUtils.isBlank(commitOrderReq.getName())) {
            throw new OrderException(-1, "name can not be empty");
        }
        if (StringUtils.isBlank(commitOrderReq.getMobile())) {
            throw new OrderException(-1, "mobile can not be empty");
        }
        if (StringUtils.isBlank(commitOrderReq.getEmail())) {
            throw new OrderException(-1, "email can not be empty");
        }
        if (StringUtils.isBlank(commitOrderReq.getAddress())) {
            throw new OrderException(-1, "address can not be empty");
        }
        if (StringUtils.isBlank(commitOrderReq.getCity())) {
            throw new OrderException(-1, "city can not be empty");
        }
        if (StringUtils.isBlank(commitOrderReq.getAddress())) {
            throw new OrderException(-1, "email can not be empty");
        }
    }
}
