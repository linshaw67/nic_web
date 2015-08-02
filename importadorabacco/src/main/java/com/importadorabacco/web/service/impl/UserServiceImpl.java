package com.importadorabacco.web.service.impl;

import com.importadorabacco.web.exception.BusinessException;
import com.importadorabacco.web.model.ResetToken;
import com.importadorabacco.web.model.User;
import com.importadorabacco.web.service.BaseService;
import com.importadorabacco.web.service.EmailService;
import com.importadorabacco.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {
    private final static int TOKEN_EXPIRE_TIME = 3600 * 1000 * 48; // 2 days
    private final static Pattern illegalCharPattern = Pattern.compile("[^\\w\\.@-]");

    @Resource
    private EmailService emailService;

    @Override
    public User authorize(String email, String password) {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            return null;
        }

        User user = userAccountDao.selectByEmail(email.trim());
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
            throw new BusinessException(-1, "illegal email: " + email);
        }

        User existsUser = userAccountDao.selectByEmail(email);
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
            userAccountDao.insert(user);
        } else {
            userAccountDao.update(user);
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

        User user = userAccountDao.selectById(uid);
        if (user == null || !user.isTokenValid(token)) {
            logger.info("op=verify failed, uid={}, token={}, user={}", uid, token, user);
            return false;
        }
        if (!user.isActive()) {
            user.active();
            userAccountDao.update(user);
        }

        logger.info("op=verify success, uid={}, token={}", uid, token);
        return true;
    }

    @Override
    public boolean isUserExists(Long uid) {
        if (uid == null) {
            return false;
        }
        User user = userAccountDao.selectById(uid);
        return user != null && user.isActive();
    }

    @Override
    public boolean isUserExists(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        User user = userAccountDao.selectByEmail(email);
        return user != null && user.isActive();
    }

    @Override
    public User queryUserById(Long uid) {
        if (uid == null) {
            return null;
        }
        return userAccountDao.selectById(uid);
    }

    @Transactional
    @Override
    public boolean sendResetEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        email = email.trim();
        User user = userAccountDao.selectByEmail(email);
        if (user == null) {
            logger.error("op=sendResetEmail user does not exists, email={}", email);
            throw new BusinessException(-1, "user does not exists");
        }
        if (!user.isActive()) {
            logger.error("op=sendResetEmail user is not active, email={}", email);
            throw new BusinessException(-1, "user is not active, please active it first");
        }

        ResetToken resetToken = userAccountResetDao.selectLastUnusedToken(email);
        if (resetToken == null) {
            String token = KeyGenerators.string().generateKey();
            Timestamp tokenExpireTime = new Timestamp(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);
            resetToken = new ResetToken(email, token, tokenExpireTime);
            userAccountResetDao.insert(resetToken);
        }
        emailService.sendResetEmail(email, resetToken.getToken());

        return true;
    }

    @Transactional
    @Override
    public boolean resetAccount(String token, String pwd) {
        logger.info("op=resetAccount token={}", token);
        if (StringUtils.isBlank(token) || StringUtils.isBlank(pwd)) {
            return false;
        }
        ResetToken resetToken = userAccountResetDao.selectByToken(token);
        if (resetToken == null) {
            return false;
        }
        if (!resetToken.isValid()) {
            logger.error("op=resetAccount token is expired, token={}", resetToken);
            throw new BusinessException(-1, "the url is expired");
        }
        userAccountResetDao.updateStatus(token, 1);

        pwd = pwd.trim();
        User user = userAccountDao.selectByEmail(resetToken.getEmail());
        String pwdHash = BCrypt.hashpw(pwd, BCrypt.gensalt());
        user.setPwd(pwdHash);
        userAccountDao.update(user);

        logger.info("op=resetAccount complete token={}", token);
        return true;
    }

    @Override
    public boolean verifyResetToken(String token) {
        logger.info("op=verifyResetEmail token={}", token);
        if (StringUtils.isBlank(token)) {
            return false;
        }
        ResetToken resetToken = userAccountResetDao.selectByToken(token);
        logger.info("op=verifyResetEmail result={}, token={}", resetToken, token);
        if (resetToken == null) {
            return false;
        }
        if (!resetToken.isValid()) {
            logger.error("op=verifyResetEmail token is expired, token={}", resetToken);
            throw new BusinessException(-1, "the url is expired");
        }
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
