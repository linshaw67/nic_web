package com.importadorabacco.web.util;

import com.google.common.base.Splitter;
import com.importadorabacco.web.model.MailPush;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * email util
 */
public class MailUtil {

    private static Logger logger = LoggerFactory.getLogger(MailUtil.class);

    public static String EMAIL_SPLIT = "-";

    private static MailUtil instance = null;

    private MailUtil() {
    }

    public synchronized static MailUtil getInstance() {
        if (instance == null) {
            instance = new MailUtil();
        }
        return instance;
    }

    /**
     * send email to one receiver
     *
     * @param mail mail info
     * @return success or not
     */
    public boolean sendSingleMail(MailPush mail) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName(mail.getServerAddress());
            email.setAuthentication(mail.getSenderUsername(), mail.getSenderPassword());
            email.setCharset(mail.getCharset());
            email.addTo(mail.getToAddress());
            email.setFrom(mail.getSenderAddress());
            email.setSubject(mail.getSubject() + (mail.getEmailSource() == null ? "" : mail.getEmailSource()));
            email.setMsg(mail.getContent());
            email.send();
            return true;
        } catch (Exception e) {
            logger.error("email send failed!", e);
            return false;
        }

    }

    /**
     * send html email to multi receivers
     *
     * @param mail mail info
     * @return success or not
     */
    public boolean sendSingleHtmlMail(MailPush mail) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setSSLOnConnect(true);
            email.setHostName(mail.getServerAddress());
            email.setAuthentication(mail.getSenderUsername(), mail.getSenderPassword());
            email.setCharset(mail.getCharset());
            for (String s : Splitter.on(";").split(mail.getToAddresses())) {
                email.addTo(s);
            }
            email.setFrom(mail.getSenderAddress());
            email.setSubject(mail.getSubject() + (mail.getEmailSource() == null ? "" : mail.getEmailSource()));
            email.setHtmlMsg(mail.getContent());
            email.send();
            logger.info("send email, {}", email);
            return true;
        } catch (Exception e) {
            logger.error("email send failed!", e);
            return false;
        }

    }

    /**
     * send html email to multi receivers which split by ';'
     *
     * @param mail mail info
     * @return success or not
     */
    public boolean sendMail(MailPush mail) {
        String emailListStr = mail.getToAddresses();
        if (StringUtils.isNotBlank(emailListStr)) {
            String[] emailArr = emailListStr.split(";");
            for (String email : emailArr) {
                mail.setToAddress(email);
                sendSingleMail(mail);
            }
            return true;
        }
        return true;
    }

    /**
     * send html email to multi receivers which split by ';'
     *
     * @param mail mail info
     * @return success or not
     */
    public boolean sendHtmlMail(MailPush mail) {
        String emailListStr = mail.getToAddresses();
        if (StringUtils.isNotBlank(emailListStr)) {
            sendSingleHtmlMail(mail);
            //            String[] emailArr = emailListStr.split(";");
            //            for(int i = 0 ; i < emailArr.length ; i++){
            //                String email = emailArr[i];
            //                mail.setToAddress(email);
            //                sendSingleHtmlMail(mail);
            //            }
            return true;
        }
        return true;
    }

    public static void main(String[] args) {
    }
}
