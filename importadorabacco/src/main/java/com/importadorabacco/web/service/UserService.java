package com.importadorabacco.web.service;

import com.importadorabacco.web.exception.BusinessException;
import com.importadorabacco.web.model.User;

/**
 * Created by tanhengyi on 15-5-13.
 */
public interface UserService {
    /**
     * authorize user account
     *
     * @param email user email
     * @param password password
     * @return user account info
     */
    User authorize(String email, String password);

    /**
     * authorize user account
     *
     * @param email user email
     * @param password password
     * @return user account info
     * @throws BusinessException when user exists
     */
    User register(String email, String password) throws BusinessException ;

    /**
     * verify user account
     *
     * @param uid user id
     * @param token verify token
     * @return success or not
     */
    boolean verify(Long uid, String token);
}
