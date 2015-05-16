package com.importadorabacco.web.security.impl;

import com.google.common.collect.Lists;
import com.importadorabacco.web.exception.BusinessException;
import com.importadorabacco.web.model.User;
import com.importadorabacco.web.security.Encryptor;
import com.importadorabacco.web.security.SecurityContext;
import com.importadorabacco.web.security.constant.Cookies;
import com.importadorabacco.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tanhengyi on 15-5-16.
 */
@Component
public class SecurityContextImpl implements SecurityContext {
    private static final Logger logger = LoggerFactory.getLogger(SecurityContextImpl.class);

    private static final String WEB_ROOT = "/";
    private static final long expireTime = 3600 * 1000 * 2; // 2 hours
    private static final List<String> cookieList = Lists
            .newArrayList(Cookies.UID, Cookies.USERNAME, Cookies.LOGIN_TIME, Cookies.TOKEN);
    private static final String SALT = "8b355de8017f8273";

    @Resource
    private UserService userService;

    @Resource
    private Encryptor encryptor;

    @Override
    public boolean isLogin() {
        return isLogin(getCurrentRequest());
    }

    @Override
    public boolean logout(HttpServletResponse response) {
        return logout(getCurrentRequest(), response);
    }

    @Override
    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = cleanCookies(request.getCookies());
        for (Cookie cookie : cookies) {
            response.addCookie(cookie);
        }
        return true;
    }

    @Override
    public boolean isLogin(HttpServletRequest request) {
        return isValid(request.getCookies());
    }

    @Override
    public boolean tryLogin(String username, String pwd, HttpServletResponse response) throws BusinessException {
        String ip = getCurrentRequest().getRemoteAddr();
        User user = userService.authorize(username, pwd);
        if (user == null) {
            logger.info("user[{}] from ip[{}] login authorize failed", username, ip);
            return false;
        }

        if (!user.isActive()) {
            logger.info("user[{}] from ip[{}] login is not active", username, ip);
            throw new BusinessException(-1,
                    "the accout has not been verified, access the verification link in you mail box to active it");
        }

        List<Cookie> cookies = generateCookies(user);
        for (Cookie cookie : cookies) {
            response.addCookie(cookie);
        }
        logger.info("user[{}] from ip[{}] login success", username, ip);

        return true;
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private List<Cookie> generateCookies(User user) {
        HttpServletRequest request = getCurrentRequest();

        String ip = request.getRemoteAddr();
        Long currentTime = System.currentTimeMillis();

        String authToken = encryptor.encrypt(user.getId() + user.getEmail() + ip + currentTime, SALT);

        Cookie uid = new Cookie(Cookies.UID, user.getId().toString());
        Cookie email = new Cookie(Cookies.USERNAME, user.getEmail());
        Cookie loginTime = new Cookie(Cookies.LOGIN_TIME, currentTime.toString());
        Cookie token = new Cookie(Cookies.TOKEN, authToken);

        List<Cookie> cookies = Lists.newArrayList(uid, email, loginTime, token);
        setCookieAttribute(cookies);

        return cookies;
    }

    private boolean isValid(Cookie[] cookies) {
        if (cookies == null) {
            return false;
        }

        Map<String, String> cookieMap = new HashMap<String, String>();
        for (Cookie cookie : cookies) {
            if (cookieList.contains(cookie.getName())) {
                if (cookie.getValue().trim().isEmpty()) {
                    return false;
                }
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }

        long loginTime = 0;
        try {
            loginTime = Long.parseLong(cookieMap.get(Cookies.LOGIN_TIME));
        } catch (NumberFormatException e) {
            return false;
        }

        if (loginTime + expireTime <= System.currentTimeMillis()) {
            return false;
        }

        HttpServletRequest request = getCurrentRequest();
        String ip = request.getRemoteAddr();
        String rawStr = cookieMap.get(Cookies.UID)
                + cookieMap.get(Cookies.USERNAME)
                + ip
                + cookieMap.get(Cookies.LOGIN_TIME);
        String encStr = cookieMap.get(Cookies.TOKEN);

        return encryptor.isEncStrValid(encStr, rawStr, SALT);
    }

    private void setCookieAttribute(List<Cookie> cookies) {
        // set path
        for (Cookie cookie : cookies) {
            cookie.setPath(WEB_ROOT);
            cookie.setMaxAge((int) expireTime);
        }
    }

    private Cookie[] cleanCookies(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookieList.contains(cookie.getName())) {
                cookie.setPath(WEB_ROOT);
                cookie.setMaxAge(0);
            }
        }

        return cookies;
    }

}
