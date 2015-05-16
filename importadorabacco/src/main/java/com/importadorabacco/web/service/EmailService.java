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
    public void sendHtmlEmail(String subject, String content, String addresses);

    /**
     * send order email
     *
     * @param orderInfo orderInfo
     */
    public void sendOrderEmail(OrderInfo orderInfo);

    /**
     * send register email
     *
     * @param user user register info
     */
    public void sendRegisterEmail(User user);
}
