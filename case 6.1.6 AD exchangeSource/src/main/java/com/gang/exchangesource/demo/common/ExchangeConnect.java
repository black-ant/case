package com.gang.exchangesource.demo.common;

import com.gang.exchangesource.demo.to.MailSystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.SocketFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @Classname ExchangeConnect
 * @Description TODO
 * @Date 2020/2/28 18:57
 * @Created by zengzg
 */
@Component
public class ExchangeConnect {

    private static Logger LOG = LoggerFactory.getLogger(ExchangeConnect.class);

    private static final String CHARSETNAME = "UTF-8";
    private static final Integer NETWORK_PORT = 8885;
    private static final Integer NETWORK_TIMEOUT = 30000;

    private Socket socket = null;
    private MailSystemConfig config;

    public static ExchangeConnect build(MailSystemConfig config) {
        ExchangeConnect connect = new ExchangeConnect();
        connect.setConfig(config);
        try {
            connect.setSocket(connect.createServerSocket(config.getHost(), config.getPort()));
        } catch (Exception e) {
            LOG.error("E----> 创建连接异常");
            LOG.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
        return connect;
    }


    /**
     * 建立 Server Socket 连接
     *
     * @param host
     * @param port
     * @return
     * @throws UnknownHostException
     * @throws IOException
     */
    private Socket createServerSocket(String host, int port) throws UnknownHostException, IOException {

        // Step 1 : Get Factory
        SocketFactory factory = SocketFactory.getDefault();

        // Step 2 : Create Socket
        Socket socket = factory.createSocket(host, port);
        socket.setSoTimeout(NETWORK_TIMEOUT);

        return socket;
    }

    /**
     * 对 Exchange Server 进行操作
     *
     * @param str
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String doOperation(String str) throws IOException, InterruptedException {
        return doOperation(this.socket, str);
    }

    /**
     * 对 Exchange Server 进行操作
     *
     * @param socket
     * @param str
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private String doOperation(Socket socket, String str) throws IOException, InterruptedException {
        // 向本机的52000端口发出客户请求

        // 构建IO
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, CHARSETNAME));
        // 向服务器端发送一条消息
        bw.write(str);
        bw.flush();

        // 读取服务器返回的消息
        BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARSETNAME));
        //Thread.sleep(timeout_network);
        char[] readLine = new char[5];
        br.read(readLine);
        return String.valueOf(readLine).trim();
    }

    /**
     * 关闭连接
     */
    public void close() {

    }

    public MailSystemConfig getConfig() {
        return config;
    }

    public void setConfig(MailSystemConfig config) {
        this.config = config;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
