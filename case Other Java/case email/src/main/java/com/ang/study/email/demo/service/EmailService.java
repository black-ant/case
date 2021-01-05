package com.ang.study.email.demo.service;


import com.alibaba.fastjson.JSONObject;
import com.ang.study.email.demo.entity.EmailContent;
import com.ang.study.email.demo.entity.EmailSetting;
import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements ApplicationRunner {


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        send();
    }


    //        String setting = "{'permissionAccount':'2331746732@qq.com','permissionPassword':'wyiyyjowgxtndjde'," +
    //                "'protocol':'SMTP','host':'smtp.qq.com','port':'465','auth':'true','enable':true,'time':20}";


    public void send() {
        String eamilSetting;

        EmailContent msgContent;

        JSONObject settingJson = new JSONObject();

        settingJson.put("account", "2331746732@qq.com");
        settingJson.put("password", "wyiyyjowgxtndjde");

        settingJson.put("protocol", "SMTP");
        settingJson.put("host", "smtp.qq.com");
        settingJson.put("port", "465"); // 587 \ 465
        settingJson.put("auth", "true");
        settingJson.put("enable", true);
        settingJson.put("time", 20);

        eamilSetting = settingJson.toJSONString();

        logger.info("------> setting json :{} <-------", eamilSetting);

        logger.info("------> 创建 Content <-------");
        msgContent = new EmailContent();
        msgContent.setTitle("test send eamil");
        msgContent.setContent("this is a email msg");
        msgContent.setReceiverUser("zengzg@paraview.cn");
        msgContent.setType("txt");

        send(eamilSetting, msgContent);
    }

    static class MyAuthenricator extends Authenticator {
        private String u = null;
        private String p = null;

        MyAuthenricator(String u, String p) {
            this.u = u;
            this.p = p;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(u, p);
        }
    }

    public String send(String setting, EmailContent sendContent) {

        EmailSetting emailSetting = createObjSetting(setting);

        Properties properties = new Properties();
        createProperties(setting, properties, emailSetting);

        sendHandle(sendContent, properties, emailSetting);
        return "邮箱 : " + sendContent.getReceiverUser() + " : " + sendContent.getTitle() + " : " + "发送成功";
    }

    public EmailSetting createObjSetting(String setting) {
        try {
            logger.info("------> setting is :{} <-------", setting.toString());
            EmailSetting emailSetting = JSONObject.parseObject(setting, EmailSetting.class);
            return emailSetting;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void createProperties(String setting, Properties properties, EmailSetting emailSetting) {

        properties.setProperty("mail.transport.protocol", emailSetting.getProtocol());
        properties.setProperty("mail.smtp.host", emailSetting.getHost());
        properties.setProperty("mail.smtp.port", emailSetting.getPort());
        properties.setProperty("mail.smtp.auth", emailSetting.getAuth());
        properties.setProperty("mail.smtp.timeout", String.valueOf(emailSetting.getTime() * 2000));
        try {
            if (emailSetting.isEnable()) {
                logger.info("------> open ssl policy <-------");
                MailSSLSocketFactory sf = null;
                sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                if ("465".equals(emailSetting.getPort()) || "994".equals(emailSetting.getPort())) {
                    properties.put("mail.smtp.ssl.enable", "true");
                }
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.ssl.socketFactory", sf);

                logger.info("------> open ssl policy end<-------");
            }
        } catch (GeneralSecurityException e) {
            logger.error("E----> server error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
    }

    public void sendHandle(EmailContent sendContent, Properties properties, EmailSetting emailSetting) {

        logger.info("------> send before <-------");
        Session session = Session.getInstance(properties,
                new MyAuthenricator(emailSetting.getAccount(), emailSetting.getPassword()));
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(emailSetting.getAccount(), ""));
            mimeMessage.setSubject(sendContent.getTitle());
            mimeMessage.setSentDate(new Date());
            mimeMessage.setText(sendContent.getContent());
            mimeMessage.setContent(sendContent.getContent(), "text/html;charset =utf-8");
            mimeMessage.saveChanges();

            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(sendContent.getReceiverUser()));
            Transport.send(mimeMessage);

        } catch (Exception e) {

            logger.error("E----> send error  :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
    }


}
