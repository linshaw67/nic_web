package com.importadorabacco.web.controller;

import com.importadorabacco.web.exception.BusinessException;
import com.importadorabacco.web.model.User;
import com.importadorabacco.web.model.domain.ApiResp;
import com.importadorabacco.web.security.SecurityContext;
import com.importadorabacco.web.service.EmailService;
import com.importadorabacco.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private SecurityContext securityContext;

    @Resource
    private UserService userService;

    @Resource
    private EmailService emailService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp login(@RequestParam("username") String username,
            @RequestParam("pwd") String pwd,
            HttpServletResponse response) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(pwd)) {
            return ApiResp.failed("username and password can not be empty");
        }
        try {
            return securityContext.tryLogin(username, pwd, response) ?
                    ApiResp.success() :
                    ApiResp.failed("username and password mismatch");
        } catch (BusinessException e) {
            return new ApiResp<>(e.getCode(), e.getMessage(), e.getMessage());
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp register(@RequestParam("username") String username, @RequestParam("pwd") String pwd) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(pwd)) {
            return ApiResp.failed("username and password can not be empty");
        }

        User user;
        try {
            user = userService.register(username, pwd);
        } catch (BusinessException e) {
            return new ApiResp<>(e.getCode(), e.getMessage(), e.getMessage());
        }

        emailService.sendRegisterEmail(user);

        return ApiResp.success();
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public ModelAndView verify(@RequestParam("u") Long uid, @RequestParam("t") String token) {
        if (uid == null || StringUtils.isBlank(token)) {
            return new ModelAndView("/html/verify").addObject("success", false);
        }

        return userService.verify(uid, token) ?
                new ModelAndView("/html/verify").addObject("success", true) :
                new ModelAndView("/html/verify").addObject("success", false);
    }

    @RequestMapping(value = "/resetAccount/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp sendResetEmail(@RequestParam("email") String email) {
        if (StringUtils.isBlank(email)) {
            return ApiResp.failed("email can not be empty");
        }

        try {
            return userService.sendResetEmail(email) ?
                    ApiResp.success() :
                    ApiResp.defaultFailed();
        } catch (BusinessException e) {
            return new ApiResp<>(e.getCode(), e.getMessage(), e.getMessage());
        } catch (Exception e) {
            logger.error("sendResetEmail failed, email: {}", email, e);
            return ApiResp.defaultFailed();
        }
    }

    @RequestMapping(value = "/resetAccount", method = RequestMethod.GET)
    public ModelAndView resetAccount(@RequestParam("t") String token) {
        if (StringUtils.isBlank(token)) {
            return new ModelAndView("/html/home");
        }

        try {
            return userService.verifyResetToken(token) ?
                    new ModelAndView("/html/reset").addObject("success", true) :
                    new ModelAndView("/html/reset").addObject("success", false).addObject("msg", "verification failed");
        } catch (BusinessException e) {
            return new ModelAndView("/html/reset").addObject("success", false).addObject("msg", e.getMessage());
        } catch (Exception e) {
            logger.error("sendResetEmail failed, token: {}", token, e);
            return new ModelAndView("/html/reset")
                    .addObject("success", false)
                    .addObject("msg", "some error occurs, please try again");
        }
    }

    @RequestMapping(value = "/resetAccount", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp resetAccount(@RequestParam("t") String token, @RequestParam("pwd") String pwd) {
        if (StringUtils.isBlank(token) || StringUtils.isBlank(pwd)) {
            return ApiResp.failed("token and password can not be empty");
        }

        try {
            return userService.resetAccount(token, pwd) ?
                    ApiResp.success() :
                    new ApiResp<>(-1, "the url is expired", "the url is expired");
        } catch (BusinessException e) {
            return new ApiResp<>(e.getCode(), e.getMessage(), e.getMessage());
        } catch (Exception e) {
            logger.error("sendResetEmail failed, token: {}", token, e);
            return ApiResp.defaultFailed();
        }
    }
}
