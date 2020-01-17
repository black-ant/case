package com.gang.study.websocket.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.websocket.module.MsgError;
import com.gang.study.websocket.module.PushMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname WebSocketService
 * @Description TODO
 * @Date 2019/12/2 16:06
 * @Created by zengzg
 */
@ServerEndpoint("/push/msg")
@Component
public class WebSocketService {

    private static Logger log = LoggerFactory.getLogger(WebSocketService.class);

    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap();

    //记录gid对应的所有session的ID, Map<gid,List<sessionId>>
    private static ConcurrentHashMap<String, List<String>> groupsSessions = new ConcurrentHashMap();

    //连接到服务器的session总数
    public static int sessionCount = 0;

    //远端服务器(dbcenter)连接, 会设置空闲时间(毫秒)
    public static final long SV_SIDE_IDLE_MILLS = 300_000L;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        log.info("#Websocket OnOpen id: {}", session.getId());
        String queryStr = session.getQueryString();
        if (queryStr != null && queryStr.matches("(^|&)ct=(?i)sv(&|$)")) {
            //其它服务器作为client连接到此Push服务器时, 会设置空闲失效时间, 在一定时间内没有收到或发送消息之一, 此session就会失效
            session.setMaxIdleTimeout(SV_SIDE_IDLE_MILLS);
        }
        sessionMap.put(session.getId(), session);
        sessionCount++;
        sendMessage(session, "连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        log.info("#Websocket OnClose id: {}", session.getId());
        sessionMap.remove(session.getId());
        sessionCount--;
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("#Websocket OnMessage > {}", message);
        PushMessage msg = JSONObject.parseObject(message, PushMessage.class);
        if (StringUtils.isEmpty(msg.getGid()) || StringUtils.isEmpty(msg.getEvent())) {
            sendMsg(session, msg.asResult(MsgError.PARAMETERS_MISSING));
            return;
        }
        switch (msg.getEvent()) {
            case "REG":
                handleRegEvent(session, msg);
                break;
            case "UNREG":
                handleUnRegEvent(session, msg);
                break;
            case "TRANS":
                handleTransEvent(session, msg);
                break;
            case "SYS":
                handleSysEvent(session, msg);
                break;
            default:
                sendMsg(session, msg.asResult(MsgError.UNSUPPORTED_MSG_EVENT));
        }

        PushMessage backMsg = new PushMessage();
        BeanUtils.copyProperties(msg, backMsg);
        backMsg.setContent("send ok");

        sendMsg(session, backMsg);

    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 取消注册消息单位
     *
     * @param session
     * @param msg
     * @throws IOException
     */
    private static void handleUnRegEvent(Session session, PushMessage msg) throws IOException {
        String gid = msg.getGid();
        List<String> hasSessions = groupsSessions.get(gid);
        if (hasSessions.isEmpty()) {
            sendMsg(session, msg.asResult(MsgError.UNREGISTERED_GID));
            return;
        }

        hasSessions.remove(session.getId());
        if (hasSessions.isEmpty()) {
            groupsSessions.remove(gid);
        }
        sendMsg(session, msg.asResult(MsgError.SUCCESS));
    }

    /**
     * 普通消息传输处理
     *
     * @param session
     * @param msg
     * @throws IOException
     */
    private static void handleTransEvent(Session session, PushMessage msg) throws IOException {

        String gid = msg.getGid();
        //所有绑定了gid的session的id列表

        List<String> hasSessions = groupsSessions.get(gid);
        if (groupsSessions == null || hasSessions == null || hasSessions.isEmpty()) {
            if (session != null) {
                sendMsg(session, msg.asResult(MsgError.UNREGISTERED_GID));
            }
            log.warn("未注册的GID: {}", gid);
            return;
        }

        //给所有绑定了GID的session发消息
        for (int i = hasSessions.size() - 1; i >= 0; i--) {
            String sid = hasSessions.get(i);
            //            log.debug("handle TRANS>> gid: {},  sid: {}", gid, sid);

            //不处理消息发起方
            if (session != null && sid.equals(session.getId())) {
                continue;
            }
            Session gsin = sessionMap.get(sid);

            //sessionMap中没有找到该sessionId的session或该session已经关闭的时候,解除此gid与session的绑定
            if (gsin == null || !gsin.isOpen()) {
                //                log.debug("sessioin[{}] is not exists or closed", sid);
                hasSessions.remove(sid);
                continue;
            }

            msg.setResultCode(0);
            sendMsg(gsin, msg);
        }

        if (session != null) {
            //其它服务器作为client连接到此Push服务器时, 处理消息后会发送成功的信息返回, 避免长时间没有返回消息触发IdleTimeout
            String queryStr = session.getQueryString();
            if (queryStr != null && queryStr.matches("(^|&)ct=(?i)sv(&|$)")) {
                sendMsg(session, msg.asResult(MsgError.SUCCESS));
            }
        }
    }

    /**
     * 注册消息单位
     *
     * @param session
     * @param msg
     * @throws IOException
     */
    private static void handleRegEvent(Session session, PushMessage msg) throws IOException {
        String gid = msg.getGid();
        if (!groupsSessions.containsKey(gid)) {
            groupsSessions.put(gid, new ArrayList());
        }
        List<String> hasSessions = groupsSessions.get(gid);
        String sid = session.getId();
        if (!hasSessions.contains(sid)) {
            hasSessions.add(sid);
        }
        sendMsg(session, msg.asResult(MsgError.SUCCESS));
    }

    private static void handleSysEvent(Session session, PushMessage msg) throws IOException {
        String info = "Push Session count: " + sessionCount;
        sendMsg(session, msg.asResult(MsgError.SUCCESS, info));
    }

    private static void sendMsg(Session session, PushMessage msg) throws IOException {
        sendMsg(session, JSONObject.toJSONString(msg));
    }

    private static void sendMsg(Session session, String content) throws IOException {
        session.getAsyncRemote().sendText(content);
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(Session session, String message) throws IOException {
        log.info("------> back send Msg <-------");
        session.getBasicRemote().sendText(message);
    }


}
