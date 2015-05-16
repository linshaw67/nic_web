package com.importadorabacco.web.security;

import com.importadorabacco.web.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tanhengyi on 15-5-16.
 */
public interface SecurityContext {
    /**
     * check login state
     *
     * @return login state
     */
    boolean isLogin();

    /**
     * check login state
     *
     * @param request servlet request
     * @return login state
     */
    boolean isLogin(HttpServletRequest request);

    /**
     * try login
     *
     * @param username username
     * @param pwd password
     * @param response response
     * @return success or not
     * @throws BusinessException when account is not active
     */
    boolean tryLogin(String username, String pwd, HttpServletResponse response) throws BusinessException;

    /**
     * logout
     *
     * @param response response
     * @return success or not
     */
    boolean logout(HttpServletResponse response);

    /**
     * logout
     *
     * @param request servlet request
     * @param response response
     * @return success or not
     */
    boolean logout(HttpServletRequest request, HttpServletResponse response);
}
