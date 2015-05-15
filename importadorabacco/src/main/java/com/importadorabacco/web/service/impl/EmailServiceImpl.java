package com.importadorabacco.web.service.impl;

import com.google.common.collect.Maps;
import com.importadorabacco.web.model.MailPush;
import com.importadorabacco.web.model.domain.OrderInfo;
import com.importadorabacco.web.service.BaseService;
import com.importadorabacco.web.service.EmailService;
import com.importadorabacco.web.util.MailUtil;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
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
    private VelocityEngine velocityEngine;

    @Value("${email.subject}")
    private String EMAIL_SUBJECT;

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

    @Override
    public void sendHtmlEmail(String subject, String content, String toAddresses) {
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

    @Override
    public void sendOrderEmail(OrderInfo orderInfo) {
        if (orderInfo == null) {
            return;
        }

        logger.info("op=sendOrderEmail, orderInfo={}", orderInfo);

//        Map<String, Object> data = Maps.newHashMap();
//        data.put("orderInfo", orderInfo);
//        String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "order_email.vm", "UTF-8", data);
//        sendHtmlEmail(EMAIL_SUBJECT, content, orderInfo.getEmail());
    }
}
