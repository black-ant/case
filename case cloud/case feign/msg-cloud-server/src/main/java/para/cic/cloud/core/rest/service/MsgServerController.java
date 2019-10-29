package para.cic.cloud.core.rest.service;

import com.alibaba.fastjson.JSONObject;
import com.netflix.client.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import para.cic.cloud.core.feign.MsgServerMapper;
import para.cic.cloud.core.model.MsgSendTO;

/**
 * description
 *
 * @author zengzg@paraview.cn
 * @version 2019年9月3日
 * @since JDK 1.8
 */
@RestController
public class MsgServerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MsgServerMapper msgServerMapper;

    @GetMapping("send")
    public void sendMsg() {
        MsgSendTO sendTO = new MsgSendTO();
        sendTO.setTitle("测试发送11");
        sendTO.setContent("邮件消息11");
        sendTO.setReceiver("value");
        msgServerMapper.send(sendTO);
    }

    @GetMapping("sendext")
    public void sendExt() {
        MsgSendTO sendTO = new MsgSendTO();
        sendTO.setTitle("测试发送11");
        sendTO.setContent("邮件消息11");
        sendTO.setReceiver("value");
//        try {
        JSONObject obj = msgServerMapper.sendext(sendTO);
        logger.info("------> this is over <-------");
//        } catch (Exception e) {
//            logger.info("------> this is e  :{}--:{}<-------", e.getClass(), e.getMessage());
//            logger.info("------> : {} --{} <-------",e.getCause());
//        }

    }

    @GetMapping("sendsms")
    public void sendsms() {
        MsgSendTO sendTO = new MsgSendTO();
        sendTO.setTitle("测试发送22");
        sendTO.setContent("邮件消息122");
        sendTO.setReceiver("value");
        msgServerMapper.send(sendTO);
    }

    @GetMapping("send2")
    public void sendMsg2() {
        msgServerMapper.showUser();
    }

    public void oauth2Logic() {

    }

}
