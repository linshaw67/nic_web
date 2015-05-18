package com.importadorabacco.web.controller;

import com.importadorabacco.web.exception.BusinessException;
import com.importadorabacco.web.model.User;
import com.importadorabacco.web.model.UserCart;
import com.importadorabacco.web.model.domain.ApiResp;
import com.importadorabacco.web.model.domain.CartProductInfo;
import com.importadorabacco.web.model.domain.CommitOrderReq;
import com.importadorabacco.web.model.domain.OrderInfo;
import com.importadorabacco.web.security.SecurityContext;
import com.importadorabacco.web.security.annotation.Security;
import com.importadorabacco.web.security.constant.Cookies;
import com.importadorabacco.web.service.CartService;
import com.importadorabacco.web.service.EmailService;
import com.importadorabacco.web.service.OrderService;
import com.importadorabacco.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Resource
    private UserService userService;

    @Resource
    private CartService cartService;

    @Resource
    private OrderService orderService;

    @Resource
    private EmailService emailService;

    @Resource
    private SecurityContext securityContext;

    /**
     * query user cart
     *
     * @param userId userId
     * @return products in user cart
     */
    @Security
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
    @Security
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp add(@RequestParam("userId") Long userId,
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity,
            @CookieValue(Cookies.UID) String cookieUid,
            HttpServletResponse response) {
        if (userId == null || productId == null || quantity == null || quantity <= 0) {
            return ApiResp.failed("param error");
        }
        if (!isCurrentUser(userId, cookieUid)) {
            securityContext.logout(response);
            return ApiResp.failed("user changed, please login again");
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
    @Security
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp remove(@RequestParam("userId") Long userId,
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity,
            @CookieValue(Cookies.UID) String cookieUid,
            HttpServletResponse response) {
        if (userId == null || productId == null || quantity == null || quantity <= 0) {
            return ApiResp.failed("param error");
        }
        if (!isCurrentUser(userId, cookieUid)) {
            securityContext.logout(response);
            return ApiResp.failed("user changed, please login again");
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
    @Security
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp commitOrder(@RequestBody CommitOrderReq commitOrderReq,
            @CookieValue(Cookies.UID) String cookieUid,
            HttpServletResponse response) {
        // commit order
        OrderInfo orderInfo;
        try {
            checkCommitOrderReq(commitOrderReq);
            if (!isCurrentUser(commitOrderReq.getUserId(), cookieUid)) {
                securityContext.logout(response);
                return ApiResp.failed("user changed, please login again");
            }

            User user = userService.queryUserById(commitOrderReq.getUserId());
            if (user == null) {
                return ApiResp.failed("user does not exist");
            }

            commitOrderReq.setEmail(user.getEmail());
            orderInfo = orderService.commitOrder(commitOrderReq);
        } catch (BusinessException e) {
            return new ApiResp<>(e.getCode(), e.getMessage(), e.getMessage());
        }

        // clear cart
        cartService.clear(commitOrderReq.getUserId());
        // send email
        emailService.sendOrderEmail(orderInfo);

        return new ApiResp<>(orderInfo);
    }

    private void checkCommitOrderReq(CommitOrderReq commitOrderReq) {
        if (commitOrderReq == null) {
            throw new BusinessException(-1, "order info can not be empty");
        }
        if (commitOrderReq.getUserId() == null) {
            throw new BusinessException(-2, "need login first");
        }
        if (StringUtils.isBlank(commitOrderReq.getName())) {
            throw new BusinessException(-1, "name can not be empty");
        }
        if (StringUtils.isBlank(commitOrderReq.getMobile())) {
            throw new BusinessException(-1, "mobile can not be empty");
        }
//        if (StringUtils.isBlank(commitOrderReq.getEmail())) {
//            throw new BusinessException(-1, "email can not be empty");
//        }
        if (StringUtils.isBlank(commitOrderReq.getAddress())) {
            throw new BusinessException(-1, "address can not be empty");
        }
        if (StringUtils.isBlank(commitOrderReq.getCity())) {
            throw new BusinessException(-1, "city can not be empty");
        }
        if (StringUtils.isBlank(commitOrderReq.getAddress())) {
            throw new BusinessException(-1, "email can not be empty");
        }
    }

    private boolean isCurrentUser(Long userId, String cookieUid) {
        return userId.toString().equals(cookieUid);
    }

}
