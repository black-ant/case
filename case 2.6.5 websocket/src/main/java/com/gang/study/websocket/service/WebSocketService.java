package com.gang.study.websocket.service;

import com.alibaba.fastjson2.JSON;
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
 * WebSocket 服务端点
 * <p>
 * 处理 WebSocket 连接和消息，支持：
 * <ul>
 *     <li>客户端注册/注销</li>
 *     <li>消息转发</li>
 *     <li>分组推送</li>
 * </ul>
 * </p>
 * 
 * <h3>消息事件类型：</h3>
 * <ul>
 *     <li>REG - 注册到分组</li>
 *     <li>UNREG - 从分组注销</li>
 *     <li>TRANS - 转发消息</li>
 *     <li>SYS - 系统信息</li>
 * </ul>
 * 
 * <h3>连接地址：</h3>
 * <pre>
 * ws://localhost:8080/push/msg
 * </pre>
 *
 * @author zengzg
 * @since 2019/12/2
 */
@ServerEndpoint("/push/msg")
@Component
public class WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    /** 所有会话的映射 Map<sessionId, Session> */
    private static final ConcurrentHashMap<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

    /** 分组与会话的映射 Map<groupId, List<sessionId>> */
    private static final ConcurrentHashMap<String, List<String>> GROUP_SESSIONS = new ConcurrentHashMap<>();

    /** 连接到服务器的会话总数 */
    private static int sessionCount = 0;

    /** 服务端连接空闲超时时间（毫秒） */
    public static final long SV_SIDE_IDLE_MILLS = 300_000L;

    /**
     * 连接建立成功时调用
     *
     * @param session WebSocket 会话
     * @throws IOException IO 异常
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        log.info("WebSocket connected: sessionId={}", session.getId());
        
        String queryStr = session.getQueryString();
        if (queryStr != null && queryStr.matches("(^|&)ct=(?i)sv(&|$)")) {
            // 服务端连接设置空闲超时
            session.setMaxIdleTimeout(SV_SIDE_IDLE_MILLS);
        }
        
        SESSION_MAP.put(session.getId(), session);
        sessionCount++;
        
        sendMessage(session, "连接成功，当前在线：" + sessionCount);
    }

    /**
     * 连接关闭时调用
     *
     * @param session WebSocket 会话
     */
    @OnClose
    public void onClose(Session session) {
        log.info("WebSocket closed: sessionId={}", session.getId());
        SESSION_MAP.remove(session.getId());
        sessionCount--;
    }

    /**
     * 收到客户端消息时调用
     *
     * @param message 客户端发送的消息
     * @param session WebSocket 会话
     * @throws IOException IO 异常
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("Received message: {}", message);
        
        PushMessage msg = JSON.parseObject(message, PushMessage.class);
        
        // 验证必要参数
        if (StringUtils.isEmpty(msg.getGid()) || StringUtils.isEmpty(msg.getEvent())) {
            sendMsg(session, msg.asResult(MsgError.PARAMETERS_MISSING));
            return;
        }
        
        // 根据事件类型处理
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

        // 返回确认消息
        PushMessage response = new PushMessage();
        BeanUtils.copyProperties(msg, response);
        response.setContent("消息处理完成");
        sendMsg(session, response);
    }

    /**
     * 发生错误时调用
     *
     * @param session WebSocket 会话
     * @param error   错误信息
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket error: sessionId={}", session.getId(), error);
    }

    /**
     * 处理注册事件 - 将会话加入分组
     */
    private static void handleRegEvent(Session session, PushMessage msg) throws IOException {
        String gid = msg.getGid();
        GROUP_SESSIONS.computeIfAbsent(gid, k -> new ArrayList<>());
        
        List<String> sessions = GROUP_SESSIONS.get(gid);
        String sid = session.getId();
        
        if (!sessions.contains(sid)) {
            sessions.add(sid);
        }
        
        log.info("Session {} registered to group {}", sid, gid);
        sendMsg(session, msg.asResult(MsgError.SUCCESS));
    }

    /**
     * 处理注销事件 - 将会话从分组移除
     */
    private static void handleUnRegEvent(Session session, PushMessage msg) throws IOException {
        String gid = msg.getGid();
        List<String> sessions = GROUP_SESSIONS.get(gid);
        
        if (sessions == null || sessions.isEmpty()) {
            sendMsg(session, msg.asResult(MsgError.UNREGISTERED_GID));
            return;
        }

        sessions.remove(session.getId());
        if (sessions.isEmpty()) {
            GROUP_SESSIONS.remove(gid);
        }
        
        log.info("Session {} unregistered from group {}", session.getId(), gid);
        sendMsg(session, msg.asResult(MsgError.SUCCESS));
    }

    /**
     * 处理消息转发事件 - 向分组内所有会话发送消息
     */
    private static void handleTransEvent(Session session, PushMessage msg) throws IOException {
        String gid = msg.getGid();
        List<String> sessions = GROUP_SESSIONS.get(gid);
        
        if (sessions == null || sessions.isEmpty()) {
            if (session != null) {
                sendMsg(session, msg.asResult(MsgError.UNREGISTERED_GID));
            }
            log.warn("Unregistered group: {}", gid);
            return;
        }

        // 向分组内所有会话发送消息（排除发送者）
        for (int i = sessions.size() - 1; i >= 0; i--) {
            String sid = sessions.get(i);
            
            if (session != null && sid.equals(session.getId())) {
                continue;
            }
            
            Session targetSession = SESSION_MAP.get(sid);
            if (targetSession == null || !targetSession.isOpen()) {
                sessions.remove(sid);
                continue;
            }

            msg.setResultCode(0);
            sendMsg(targetSession, msg);
        }

        if (session != null) {
            String queryStr = session.getQueryString();
            if (queryStr != null && queryStr.matches("(^|&)ct=(?i)sv(&|$)")) {
                sendMsg(session, msg.asResult(MsgError.SUCCESS));
            }
        }
    }

    /**
     * 处理系统事件 - 返回系统信息
     */
    private static void handleSysEvent(Session session, PushMessage msg) throws IOException {
        String info = "当前连接数: " + sessionCount + ", 分组数: " + GROUP_SESSIONS.size();
        sendMsg(session, msg.asResult(MsgError.SUCCESS, info));
    }

    private static void sendMsg(Session session, PushMessage msg) throws IOException {
        sendMsg(session, JSON.toJSONString(msg));
    }

    private static void sendMsg(Session session, String content) throws IOException {
        session.getAsyncRemote().sendText(content);
    }

    /**
     * 服务器主动推送消息
     *
     * @param session 目标会话
     * @param message 消息内容
     * @throws IOException IO 异常
     */
    public void sendMessage(Session session, String message) throws IOException {
        log.info("Sending message to session {}", session.getId());
        session.getBasicRemote().sendText(message);
    }

    /**
     * 获取当前在线会话数
     *
     * @return 在线数
     */
    public static int getSessionCount() {
        return sessionCount;
    }
}
