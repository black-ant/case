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
 * @Classname EmailSendPicService
 * @Description TODO
 * @Date 2020/4/26 14:43
 * @Created by zengzg
 */
@Component
public class EmailSendPicService implements ApplicationRunner {

    private static Logger LOG = LoggerFactory.getLogger(EmailSendPicService.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("------> this is in html service <-------");
        //        sendPic();
    }

    public void sendPic() {
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

        LOG.info("------> setting json :{} <-------", eamilSetting);

        LOG.info("------> 创建 Content <-------");
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
            LOG.info("------> setting is :{} <-------", setting.toString());
            EmailSetting emailSetting = JSONObject.parseObject(setting, EmailSetting.class);
            return emailSetting;
        } catch (Exception e) {
            LOG.error(e.getMessage());
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
                LOG.info("------> open ssl policy <-------");
                MailSSLSocketFactory sf = null;
                sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                if ("465".equals(emailSetting.getPort()) || "994".equals(emailSetting.getPort())) {
                    properties.put("mail.smtp.ssl.enable", "true");
                }
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.ssl.socketFactory", sf);

                LOG.info("------> open ssl policy end<-------");
            }
        } catch (GeneralSecurityException e) {
            LOG.error("E----> server error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
    }

    public void sendHandle(EmailContent sendContent, Properties properties, EmailSetting emailSetting) {

        LOG.info("------> send before <-------");
        Session session = Session.getInstance(properties,
                new EmailService.MyAuthenricator(emailSetting.getAccount(), emailSetting.getPassword()));
        MimeMessage mimeMessage = new MimeMessage(session);
        try {

            //
            //            // 5. 创建图片"节点"
            //            MimeBodyPart image = new MimeBodyPart();
            //            // 读取本地文件
            //            DataHandler dh = new DataHandler(new FileDataSource("C:\\Users\\10169\\Pictures\\Saved
            //            Pictures\\git.jpg"));
            //            // 将图片数据添加到"节点"
            //            image.setDataHandler(dh);
            //            // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
            //            image.setContentID("mailTestPic");
            //
            //            // 6. 创建文本"节点"
            //            MimeBodyPart text = new MimeBodyPart();
            //            // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
            //            text.setContent("这是一张图片<br/><a href='http://www.baidu.com'><img " +
            //                    "src='cid:mailTestPic'/></a>", "text/html;charset=UTF-8");

            // Base 64 版本
            MimeBodyPart text = new MimeBodyPart();
            String baseStr = ImageToBase64("C:\\Users\\10169\\Pictures\\Saved Pictures\\git.jpg");
            text.setContent("这是一张图片<br/><a href='http://www.baidu.com'><img " +
                    "src='data:image/jpeg;base64," + baseStr + "'/></a>", "text/html;charset=UTF-8");


            // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
            MimeMultipart mm_text_image = new MimeMultipart();
            mm_text_image.addBodyPart(text);
            //            mm_text_image.addBodyPart(image);
            mm_text_image.setSubType("related");    // 关联关系

            // 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
            // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
            // 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
            MimeBodyPart text_image = new MimeBodyPart();
            text_image.setContent(mm_text_image);

            // 9. 创建附件"节点"
            MimeBodyPart attachment = new MimeBodyPart();
            // 读取本地文件
            DataHandler dh2 = new DataHandler(new FileDataSource("C:\\Users\\10169\\Documents\\test.docx"));
            // 将附件数据添加到"节点"
            attachment.setDataHandler(dh2);
            // 设置附件的文件名（需要编码）
            attachment.setFileName(MimeUtility.encodeText(dh2.getName()));

            // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(text_image);
            mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
            mm.setSubType("mixed");         // 混合关系

            mimeMessage.setContent(mm);
            //设置邮件的发送时间,默认立即发送
            mimeMessage.setSentDate(new Date());

            mimeMessage.setFrom(new InternetAddress(emailSetting.getAccount(), ""));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(sendContent.getReceiverUser()));
            Transport.send(mimeMessage);

        } catch (Exception e) {

            LOG.error("E----> send error  :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
    }

    /**
     *         data:,文本数据
     * <p>
     *         data:text/plain,文本数据
     * <p>
     *         data:text/html,HTML代码
     * <p>
     *         data:text/html;base64,base64编码的HTML代码
     * <p>
     *         data:text/css,CSS代码
     * <p>
     *         data:text/css;base64,base64编码的CSS代码
     * <p>
     *         data:text/javascript,Javascript代码
     * <p>
     *         data:text/javascript;base64,base64编码的Javascript代码
     * <p>
     *         data:image/gif;base64,base64编码的gif图片数据
     * <p>
     *         data:image/png;base64,base64编码的png图片数据
     * <p>
     *         data:image/jpeg;base64,base64编码的jpeg图片数据
     * <p>
     *         data:image/x-icon;base64,base64编码的icon图片数据
     *
     * @param imgPath
     * @return
     */
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
