package com.importadorabacco.web.service.impl;

import com.google.common.collect.Maps;
import com.importadorabacco.web.model.MailPush;
import com.importadorabacco.web.model.User;
import com.importadorabacco.web.model.domain.OrderInfo;
import com.importadorabacco.web.service.BaseService;
import com.importadorabacco.web.service.EmailService;
import com.importadorabacco.web.util.MailUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by tanhengyi on 15-5-16.
 */
@Service
public class EmailServiceImpl extends BaseService implements EmailService {
    @Resource
    private TaskExecutor taskExecutor;

    @Resource
    private VelocityEngine velocityEngine;

    @Value("${email.order.subject}")
    private String EMAIL_ORDER_SUBJECT;

    @Value("${email.order.customer.subject}")
    private String EMAIL_ORDER_CUSTOMER_SUBJECT;

    @Value("${email.register.subject}")
    private String EMAIL_REGISTER_SUBJECT;

    @Value("${email.reset.subject}")
    private String EMAIL_RESET_SUBJECT;

    @Value("${email.source}")
    private String EMAIL_SOURCE;

    @Value("${email.sender.addr}")
    private String SENDER_ADDRESS;

    @Value("${email.sender.username}")
    private String SENDER_USERNAME;

    @Value("${email.sender.pwd}")
    private String SENDER_PASSWORD;

    @Value("${email.smtp.server}")
    private String SMTP_SERVER;

    @Value("${host}")
    private String HOST;

    @Override
    public void sendHtmlEmail(final String subject, final String content, final String toAddresses) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MailPush mail = new MailPush();
                mail.setCharset("UTF-8");
                mail.setContent(content);
                mail.setEmailSource(EMAIL_SOURCE);
                mail.setSenderAddress(SENDER_ADDRESS);
                mail.setSenderUsername(SENDER_USERNAME);
                mail.setSenderPassword(SENDER_PASSWORD);
                mail.setServerAddress(SMTP_SERVER);
                mail.setSubject(subject);
                mail.setToAddresses(toAddresses);
                MailUtil.getInstance().sendHtmlMail(mail);
            }
        });
    }

    @Override
    public void sendOrderEmail(OrderInfo orderInfo) {
        if (orderInfo == null) {
            return;
        }

        logger.info("op=sendOrderEmail start, oid={}", orderInfo.getOrder().getId());

        Map<String, Object> data = Maps.newHashMap();
        data.put("order", orderInfo);
        data.put("host", HOST);
        String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "order_email.vm", "UTF-8", data);
        sendHtmlEmail(EMAIL_ORDER_SUBJECT, content, SENDER_USERNAME);
        logger.info("op=sendOrderEmail to host success, oid={}", orderInfo.getOrder().getId());

        String content4Customer = VelocityEngineUtils
                .mergeTemplateIntoString(velocityEngine, "order_email_customer.vm", "UTF-8", data);
        sendHtmlEmail(EMAIL_ORDER_CUSTOMER_SUBJECT, content4Customer, orderInfo.getOrder().getEmail());

        logger.info("op=sendOrderEmail to customer success, oid={}", orderInfo.getOrder().getId());
    }

    @Override
    public void sendRegisterEmail(User user) {
        logger.info("op=sendRegisterEmail start, user={}", user);
        if (user == null) {
            return;
        }

        Map<String, Object> data = Maps.newHashMap();
        data.put("user", user);
        data.put("host", HOST);
        String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "register_email.vm", "UTF-8", data);
        sendHtmlEmail(EMAIL_REGISTER_SUBJECT, content, user.getEmail());

        logger.info("op=sendRegisterEmail success, user={}", user);
    }

    @Override
    public void sendResetEmail(String email, String token) {
        logger.info("op=sendResetEmail start, email={}, token={}", email, token);
        if (StringUtils.isBlank(email) || StringUtils.isBlank(token)) {
            return;
        }

        Map<String, Object> data = Maps.newHashMap();
        data.put("email", email);
        data.put("token", token);
        data.put("host", HOST);

        String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "reset_email.vm", "UTF-8", data);
        sendHtmlEmail(EMAIL_RESET_SUBJECT, content, email.trim());

        logger.info("op=sendResetEmail success, email={}, token", email, token);
    }
}
