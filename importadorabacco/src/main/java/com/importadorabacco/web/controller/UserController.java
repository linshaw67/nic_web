package com.importadorabacco.web.controller;

import com.importadorabacco.web.model.domain.ApiResp;
import com.importadorabacco.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp login(@RequestParam("username")String username, @RequestParam("pwd") String pwd) {
        return ApiResp.failed("");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ApiResp register(@RequestParam("username")String username, @RequestParam("pwd") String pwd) {
        return ApiResp.failed("");
    }
}
