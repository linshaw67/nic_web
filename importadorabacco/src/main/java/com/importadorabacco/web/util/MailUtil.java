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
            //封装simple_email
            SimpleEmail email = new SimpleEmail();
            //设置服务器
            email.setHostName(mail.getServerAddress());
            //设置用户名密码
            email.setAuthentication(mail.getSenderUsername(), mail.getSenderPassword());
            //设置编码方式
            email.setCharset(mail.getCharset());
            //设置收件人
            email.addTo(mail.getToAddress());
            //设置发件人
            email.setFrom(mail.getSenderAddress());
            //设置主题
            email.setSubject(mail.getSubject() + (mail.getEmailSource() == null ? "" : mail.getEmailSource()));
            //设置详细内容
            email.setMsg(mail.getContent());
            //邮件发送
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
            //封装simple_email
            HtmlEmail email = new HtmlEmail();
            //设置服务器
            email.setHostName(mail.getServerAddress());
            //设置用户名密码
            email.setAuthentication(mail.getSenderUsername(), mail.getSenderPassword());
            //设置编码方式
            email.setCharset(mail.getCharset());
            //设置收件人
            for (String s : Splitter.on(";").split(mail.getToAddresses())) {
                email.addTo(s);
            }
            //设置发件人
            email.setFrom(mail.getSenderAddress());
            //设置主题
            email.setSubject(mail.getSubject() + (mail.getEmailSource() == null ? "" : mail.getEmailSource()));
            //设置详细内容
            email.setHtmlMsg(mail.getContent());
            //邮件发送
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
