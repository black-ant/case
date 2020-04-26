package com.ang.study.email.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.ang.study.email.demo.entity.DefaultProperties;
import com.ang.study.email.demo.entity.EmailContent;
import com.ang.study.email.demo.entity.EmailSetting;
import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

/**
 * @Classname EmailHTMLService
 * @Description TODO
 * @Date 2020/4/26 14:39
 * @Created by zengzg
 */
@Component
public class EmailHTMLService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in html service <-------");
        sendHTML();
    }

    public void sendHTML() {
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
                new EmailService.MyAuthenricator(emailSetting.getAccount(), emailSetting.getPassword()));
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            // Base 64 版本
            String baseStr = ImageToBase64("C:\\Users\\10169\\Pictures\\Saved Pictures\\git.jpg");
            String htmlBody = DefaultProperties.HTML.replace("${base64IMG}", baseStr);

            mimeMessage.setContent(htmlBody, "text/html;charset =utf-8");
            mimeMessage.setSentDate(new Date());

            mimeMessage.setFrom(new InternetAddress(emailSetting.getAccount(), ""));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(sendContent.getReceiverUser()));
            Transport.send(mimeMessage);

        } catch (Exception e) {

            logger.error("E----> send error  :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }


    }

    private static String ImageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串

        //        LOG.info("------> base 64 : {} <-------", encoder.encode(Objects.requireNonNull(data)));
        return encoder.encode(Objects.requireNonNull(data));
    }
}
