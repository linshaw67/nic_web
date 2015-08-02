package com.importadorabacco.web.service;

import com.importadorabacco.web.model.User;
import com.importadorabacco.web.model.domain.OrderInfo;

/**
 * Created by tanhengyi on 15-5-16.
 */
public interface EmailService {
    /**
     * send html email
     *
     * @param subject subject
     * @param content content
     * @param addresses address to send to
     */
    void sendHtmlEmail(String subject, String content, String addresses);

    /**
     * send order email
     *
     * @param orderInfo orderInfo
     */
    void sendOrderEmail(OrderInfo orderInfo);

    /**
     * send register email
     *
     * @param user user register info
     */
    void sendRegisterEmail(User user);

    /**
     * send reset email
     *
     * @param email email address
     * @param token reset token
     */
    void sendResetEmail(String email, String token);
}
