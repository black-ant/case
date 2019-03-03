package com.mymap.baiduapi.server;

import com.alibaba.fastjson.JSONObject;
import com.mymap.baiduapi.entity.PushError;
import com.mymap.baiduapi.entity.PushWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/26 23:13
 * @Version 1.0
 **/
@ServerEndpoint("/push")
@Component
public class WebSocketServer {

    static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    public static final long SERVER_TIMEOUT = 300_000L;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static ConcurrentHashMap<String, WebSocketServer> SocketServerMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, List<String>> gidListSessions = new ConcurrentHashMap();

    private Session session;        //当前session
    private String sid;             //当前session id
    private String groupid;         //当前groupid

    /**
     * Seesion 加入 Session 集合
     */
    public void editWebSocketServerMap(Session session) {
        this.session = session;
        this.groupid = groupid;
        this.sid = session.getId();
        SocketServerMap.put(sid, this);
        addOnlineCount();
    }

    /**
     * Seesion 加入 Group 组 , 用于组内通讯
     */
    public void editGrouidMap(String groupid) {
        if (gidListSessions.containsKey(groupid)) {
            gidListSessions.get(groupid).add(this.sid);
        } else {
            LinkedList<String> list = new LinkedList();
            list.add(this.sid);
            gidListSessions.put(groupid,list);
        }
    }

    /**
     * 配置Session 设置
     */
    public void editSession(Session session) {
        String queryStr = session.getQueryString();
        if (queryStr != null && queryStr.matches("(^|&)ct=(?i)sv(&|$)")) {
            //设置空闲时间
            session.setMaxIdleTimeout(SERVER_TIMEOUT);
        }
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        logger.info("有新窗口开始监听:" + groupid + ",当前在线人数为" + getOnlineCount());
        editSession(session);
        editWebSocketServerMap(session);
        try {
            sendMessage("websocket 连接成功");
        } catch (IOException e) {
            logger.error("websocket seesion :{} send Message 异常 ,gid: {}失败", this.sid, groupid);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        SocketServerMap.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.info("收到来自窗口" + session.getId() + "的信息:" + message);
        PushWrapper pushWrapper = JSONObject.parseObject(message,PushWrapper.class);
        switch (pushWrapper.getPushtype()) {
            case "REG":
                regHandle(session, pushWrapper);
                break;
            case "TRANS":
                transHandle(session, pushWrapper);
                break;
            default:
                sendMsg(session, pushWrapper.wrapper(PushError.ERROR_PSUHTYPE));
        }
        //群发消息
        WebSocketServer server;
        for (String key : SocketServerMap.keySet()) {
            server = SocketServerMap.get(key);
            server.sendMessage(message);

        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public void sendInfo(String message, @PathParam("gid") String gid) throws IOException {
        logger.info("推送消息到窗口" + gid + "，推送内容:" + message);
        WebSocketServer server;
        for (String key : SocketServerMap.keySet()) {
            server = SocketServerMap.get(key);
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (gid == null) {
                    server.sendMessage(message);
                } else if (server.sid.equals(gid)) {
                    server.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    private void regHandle(Session session, PushWrapper msg) throws IOException {
        String gid = msg.getGroupid();
        editGrouidMap(gid);
        List<String> hasSessions = gidListSessions.get(gid);
        String sid = session.getId();
        if (!hasSessions.contains(sid)) {
            hasSessions.add(sid);
        }
        sendMsg(session, msg.wrapper(PushError.SUCCESS));
    }

    /**
     * 普通消息传输处理
     *
     * @param session
     * @param msg
     * @throws IOException
     */
    private void transHandle(Session session, PushWrapper msg) throws IOException {
        String gid = msg.getGroupid();
        editGrouidMap(gid);
        logger.info("转发信息：msg:{}",msg.toString());
        List<String> hasSessions = gidListSessions.get(gid);
        //给所有绑定了GID的session发消息
        for (int i = hasSessions.size() - 1; i >= 0; i--) {
            String sid = hasSessions.get(i);
            logger.info("执行session：sessionid:{}",sid);
            if (session != null && sid.equals(session.getId())) {
                continue;
            }
            Session gsin = SocketServerMap.get(sid).getSession();
            logger.info("执行session：session:{}",gsin.toString());
            if (gsin == null || !gsin.isOpen()) {
                hasSessions.remove(sid);
                continue;
            }

            msg.setPushcode(0);
            sendMsg(gsin, msg);
        }

        if (session != null) {
            //其它服务器作为client连接到此Push服务器时, 处理消息后会发送成功的信息返回, 避免长时间没有返回消息触发IdleTimeout
            String queryStr = session.getQueryString();
            if (queryStr != null && queryStr.matches("(^|&)ct=(?i)sv(&|$)")) {
                sendMsg(session, msg.wrapper(PushError.SUCCESS));
            }
        }
    }

    private static void sendMsg(Session session, PushWrapper msg) throws IOException {
        sendMsg(session,JSONObject.toJSONString(msg));
    }

    private static void sendMsg(Session session, String content) throws IOException {
        session.getAsyncRemote().sendText(content);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
