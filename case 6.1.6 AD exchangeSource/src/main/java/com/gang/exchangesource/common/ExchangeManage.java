package com.gang.exchangesource.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;

import com.gang.exchangesource.type.OperationType.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ExchangeManage
 * @Description TODO
 * @Date 2020/3/2 18:23
 * @Created by zengzg
 */

public class ExchangeManage {

    static final String CHARSETNAME = "UTF-8";
    int port_network = 8885;
    int timeout_network = 10000;
    String admin;
    String passwd;
    String clientMonitorIp;
    String domainName;
    String serviceIp;

    Socket socket = null;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public ExchangeManage(String clientMonitorIp, String admin, String passwd, String serviceIp)
            throws UnknownHostException, IOException {
        super();
        this.admin = admin;
        this.passwd = passwd;
        this.clientMonitorIp = clientMonitorIp;
        this.serviceIp = serviceIp;
        socket = createServerSocket();
    }

    public ExchangeManage(String clientMonitorIp, int port_network, int timeout_network, String admin, String passwd,
                          String serviceIp) throws UnknownHostException, IOException {
        super();
        this.port_network = port_network;
        this.timeout_network = timeout_network;
        this.admin = admin;
        this.passwd = passwd;
        this.clientMonitorIp = clientMonitorIp;
        this.serviceIp = serviceIp;
        socket = createServerSocket();
    }

    private Socket createServerSocket() throws UnknownHostException, IOException {
        // TODO Auto-generated method stub
        return createServerSocket(clientMonitorIp, port_network);
    }

    private Socket createServerSocket(String host, int port) throws UnknownHostException, IOException {

        SocketFactory factory = SocketFactory.getDefault();

        Socket socket = null;

        socket = factory.createSocket(host, port);
        socket.setSoTimeout(timeout_network);

        return socket;
    }

    public String write(String str) throws IOException, InterruptedException {
        return write(this.socket, str);
    }

    private String write(Socket socket, String str) throws IOException, InterruptedException {
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

    public String enableAccount(String accountName, String mailbox, String alias, String displayName) {
        String message = null;
        try {

            log.info("ip_network: " + clientMonitorIp + " port_network: " + port_network + " timeout_network:"
                    + timeout_network);
            log.info("domainName: " + domainName + " ;serviceIp: " + serviceIp + " ;accountName:" + accountName
                    + " ;Mailbox Database:" + mailbox);

            //String command = OperationType.OperType.ENABLEMAIL.getVal() + ";" + admin + ";" + passwd + ";" + serviceIp
            //		+ ";" + accountName + ";" + mailbox;
            String command = generateExchangeCmdJson(OperType.ENABLEMAIL.getVal(), serviceIp, "", admin, passwd,
                    accountName, mailbox, alias, displayName, "", "");
            log.info("json;");
            message = write("json;" + command);
            log.info("receiver response success!");

            log.info("receiver server response message head: " + message);

        } catch (Exception e) {
            log.error("start exchange account exception:" + e.getMessage());
            return e.getMessage();
        }

        return message;
    }

    public String disableAccount(String accountName) {
        String message = null;
        try {

            log.info("ip_network: " + clientMonitorIp + " port_network: " + port_network + " timeout_network:"
                    + timeout_network);

/*			String command = (OperationType.OperType.DISABLEMAIL.getVal()) + ";" + admin + ";" + passwd + ";" +
serviceIp
					+ ";" + accountName;*/
            String command = generateExchangeCmdJson(OperType.DISABLEMAIL.getVal(), serviceIp, "", admin, passwd,
                    accountName, "", "", "", "", "");
            log.info("start receiver response......");
            message = write("json;" + command);
            log.info("receiver server response message head: " + message);
        } catch (Exception e) {
            log.info("stop exchange account exception:" + e.getMessage());
            return e.getMessage();
        }

        return message;
    }

    public String disableDistributionGroup(String identity) {
        String message = null;
        try {

            log.info("ip_network: " + clientMonitorIp + " port_network: " + port_network + " timeout_network:"
                    + timeout_network);

            String command = generateExchangeCmdJson(OperType.DISABLECOMGROUP.getVal(), serviceIp, "", admin, passwd,
                    identity, "", "", "", "", "");
            log.info("start receiver response......");
            message = write("json;" + command);
            log.info("receiver server response message head: " + message);
        } catch (Exception e) {
            log.info("remove exchange Addresslist exception:" + e.getMessage());
            return e.getMessage();
        }

        return message;
    }

    public String enableDistributionGroup(String identity, String alias, String primarySMTPAddress) {
        String message = null;
        try {

            log.info("ip_network: " + clientMonitorIp + " port_network: " + port_network + " timeout_network:"
                    + timeout_network);

			/*String command = OperationType.OperType.NEWADDR.getVal() + ";" + admin + ";" + passwd + ";" + serviceIp
					+ ";" + name + ";" + recipientContainer + ";" + includedRecipients + ";" + container + ";"
					+ displayName + ";" + identity + ";";*/
            String command = generateExchangeCmdJson(OperType.ENABLECOMGROUP.getVal(), serviceIp, "", admin, passwd,
                    identity, "", alias, "", primarySMTPAddress, "");
            log.info("start receiver response......");
            message = write("json;" + command);
            log.info("receiver server response message head: " + message);
        } catch (Exception e) {
            log.info("new  exchange Addresslist exception:" + e.getMessage());
            return e.getMessage();
        }

        return message;
    }

    public String moveAddressList(String identity, String target) {
        String message = null;
        try {

            log.info("ip_network: " + clientMonitorIp + " port_network: " + port_network + " timeout_network:"
                    + timeout_network);

            log.info("start receiver response......");
            message = write("json;" + "");
            log.info("receiver server response message head: " + message);
        } catch (Exception e) {
            log.info("updateAddressList exchange  exception:" + e.getMessage());
            return e.getMessage();
        }

        return message;
    }

    public String setDistributionGroup(String identity, String alias, String displayName) {
        String message = null;
        try {

            log.info("ip_network: " + clientMonitorIp + " port_network: " + port_network + " timeout_network:"
                    + timeout_network);

			/*String command = OperationType.OperType.DISABLEMAIL.getVal() + ";" + admin + ";" + passwd + ";" +
			serviceIp
					+ ";" + identity + ";" + name + ";" + displayName + ";";*/
            String command = generateExchangeCmdJson(OperType.SETCOMGROUP.getVal(), serviceIp, "", admin, passwd,
                    identity, "", alias, displayName, "", "");
            log.info("start receiver response......");
            message = write("json;" + command);
            log.info("receiver server response message head: " + message);
        } catch (Exception e) {
            log.info("set exchange AddressList exception:" + e.getMessage());
            return e.getMessage();
        }

        return message;
    }

    public String getDistributionGroup(String identity) {
        String message = null;
        try {

            log.info("ip_network: " + clientMonitorIp + " port_network: " + port_network + " timeout_network:"
                    + timeout_network);

            String command = generateExchangeCmdJson(OperType.GETCOMGROUP.getVal(), serviceIp, "", admin, passwd,
                    identity, "", "", "", "", "");
            log.info("start receiver response......");
            message = write("json;" + command);
            log.info("receiver server response message head: " + message);
        } catch (Exception e) {
            log.info("get exchange AddressList exception:" + e.getMessage());
            return e.getMessage();
        }

        return message;
    }

    public String getMailBox(String identity) {
        String message = null;
        try {

            log.info("ip_network: " + clientMonitorIp + " port_network: " + port_network + " timeout_network:"
                    + timeout_network);

            String command = generateExchangeCmdJson(OperType.GETMAILBOX.getVal(), serviceIp, "", admin, passwd,
                    identity, "", "", "", "", "");
            log.info("start receiver response......");
            message = write("json;" + command);
            log.info("receiver server response message head: " + message);
        } catch (Exception e) {
            log.info("get exchange AddressList exception:" + e.getMessage());
            return e.getMessage();
        }

        return message;
    }

    public void clean() throws IOException {
        socket.close();
    }

    public static String generateExchangeCmdJson(String cmdType, String serviceIp, String domainName, String userName,
                                                 String pwd, String identity, String database, String alias,
                                                 String displayName, String primarySMTPAddress,
                                                 String recursive) throws JsonProcessingException {
        if (StringUtils.isEmpty(cmdType)) {
            return cmdType;
        }
        //        ObjectMapper mapper = new ObjectMapper();
        //        Map<String, String> map = new HashMap<>();
        //        map.put("cmdlet", cmdType);
        //        map.put("ip", serviceIp);
        //        map.put("username", userName);
        //        map.put("password", pwd);
        //        String gere = generateExchangeCmdParams(cmdType, identity, database, alias, displayName,
        //        primarySMTPAddress,
        //                recursive);
        //        map.put("param", gere);
        //        String json = mapper.writeValueAsString(map);
        //        json = json.replace("\"[", "[");
        //        json = json.replace("]\"", "]");
        //        json = json.replaceAll("\\\\\"", "\"");
        return null;
    }

    public static String generateExchangeCmdParams(String cmdType, String identity, String database, String alias,
                                                   String displayName, String primarySMTPAddress, String recursive) throws JsonProcessingException {

        List<Map<String, String>> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(identity)) {
            Map<String, String> idmap = new HashMap<String, String>();
            idmap.put("name", "Identity");
            idmap.put("value", identity);
            params.add(idmap);
        }
        if (OperType.ENABLEMAIL.getVal().equals(cmdType)) {
            if (StringUtils.isNotEmpty(database)) {
                Map<String, String> identity_obj = new HashMap<String, String>();
                identity_obj.put("name", "Database");
                identity_obj.put("value", database);
                params.add(identity_obj);
            }
            if (StringUtils.isNotEmpty(alias)) {
                Map<String, String> alias_obj = new HashMap<String, String>();
                alias_obj.put("name", "Alias");
                alias_obj.put("value", alias);
                params.add(alias_obj);
            }
        }
        //		if(OperType.DISABLEMAIL.getVal().equals(cmdType)){
        //			Map<String, String> confirm_obj = new HashMap<String, String>();
        //			confirm_obj.put("name", "Confirm:$false");
        //			confirm_obj.put("value", "");
        //			params.add(confirm_obj);
        //		}
        if (OperType.ENABLECOMGROUP.getVal().equals(cmdType)) {
            if (StringUtils.isNotEmpty(primarySMTPAddress)) {
                Map<String, String> smtObj = new HashMap<String, String>();
                smtObj.put("name", "PrimarySMTPAddress");
                smtObj.put("value", primarySMTPAddress);
                params.add(smtObj);
            }
            if (StringUtils.isNotEmpty(alias)) {
                Map<String, String> alias_obj = new HashMap<String, String>();
                alias_obj.put("name", "Alias");
                alias_obj.put("value", alias);
                params.add(alias_obj);
            }
        }
        if (OperType.DISABLECOMGROUP.getVal().equals(cmdType)) {
            Map<String, String> confirm_obj = new HashMap<String, String>();
            confirm_obj.put("name", "Confirm:$false");
            confirm_obj.put("value", "");
            params.add(confirm_obj);
        }
        if (OperType.GETCOMGROUP.getVal().equals(cmdType)) {

        }
        if (OperType.SETCOMGROUP.getVal().equals(cmdType)) {
            if (StringUtils.isNotEmpty(alias)) {
                Map<String, String> alias_obj = new HashMap<String, String>();
                alias_obj.put("name", "Alias");
                alias_obj.put("value", alias);
                params.add(alias_obj);
            }
            if (StringUtils.isNotEmpty(displayName)) {
                Map<String, String> disName_obj = new HashMap<String, String>();
                disName_obj.put("name", "DisplayName");
                disName_obj.put("value", displayName);
                params.add(disName_obj);
            }
        }
        //        ObjectMapper mapper = new ObjectMapper();
        //        String value = mapper.writeValueAsString(params);
        //        return value;
        return null;
    }

    public static void main(String[] args) {
        try {
            String name = "Cynthia";
            //            LdapbyUser ldapbyUser = new LdapbyUser("", "");
            //            String str = new LdapbyUser("", "").search(name);
            //            System.out.println(str);
            ExchangeManage exchangeManage = new ExchangeManage("192.168.220.136", 8885, 10000, "para\\administrator",
                    "Hlq961021", "192.168.220.134");
            exchangeManage.createServerSocket();
            //            exchangeManage.disableAccount(str);
            exchangeManage.disableAccount(name);
            exchangeManage.clean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
