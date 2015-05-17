package com.importadorabacco.web.service.impl;

import com.importadorabacco.web.exception.BusinessException;
import com.importadorabacco.web.model.User;
import com.importadorabacco.web.service.BaseService;
import com.importadorabacco.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {
    private final static int TOKEN_EXPIRE_TIME = 3600 * 1000 * 48; // 2 days
    private Pattern illegalCharPattern = Pattern.compile("[^\\w\\.@]");

    @Override
    public User authorize(String email, String password) {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            return null;
        }

        User user = userDao.selectByEmail(email.trim());
        if (user == null) {
            return null;
        }

        return BCrypt.checkpw(password.trim(), user.getPwd()) ? user : null;
    }

    @Transactional
    @Override
    public User register(String email, String password) throws BusinessException {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            return null;
        }

        email = email.trim();
        password = password.trim();

        Matcher matcher = illegalCharPattern.matcher(email);
        if (matcher.find()) {
            logger.warn("op=register illegal email: {}", email);
            throw new BusinessException(-1, "illegal email");
        }

        User existsUser = userDao.selectByEmail(email);
        if (existsUser != null && existsUser.isActive()) {
            throw new BusinessException(-1, "user exists");
        } else if (existsUser != null && existsUser.getTokenExpireTime().getTime() > System.currentTimeMillis()) {
            throw new BusinessException(-1, "user exists, access the verification link in you mail box to active it");
        }

        String pwdHash = BCrypt.hashpw(password, BCrypt.gensalt());
        String token = KeyGenerators.string().generateKey();

        Timestamp tokenExpireTime = new Timestamp(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);
        User user = new User(email, pwdHash, token, tokenExpireTime);
        if (existsUser == null) {
            userDao.insert(user);
        } else {
            userDao.update(user);
        }

        return user;
    }

    @Override
    public boolean verify(Long uid, String token) {
        logger.info("op=verify, uid={}, token={}", uid, token);
        if (uid == null || StringUtils.isBlank(token)) {
            logger.info("op=verify failed, uid={}, token={}", uid, token);
            return false;
        }

        User user = userDao.selectById(uid);
        if (user == null || !user.isTokenValid(token)) {
            logger.info("op=verify failed, uid={}, token={}, user={}", uid, token, user);
            return false;
        }
        if (!user.isActive()) {
            user.active();
            userDao.update(user);
        }

        logger.info("op=verify success, uid={}, token={}", uid, token);
        return true;
    }

    public static void main(String[] a) {
        String salt = BCrypt.gensalt();
        String hash = BCrypt.hashpw("123456789", salt);
        boolean ret = BCrypt.checkpw("123456", hash);
        String encode = KeyGenerators.string().generateKey();

        System.out.println("hash: " + hash);
        System.out.println("salt: " + salt);
        System.out.println("encode: " + encode);
        System.out.println("ret: " + ret);
    }
}
