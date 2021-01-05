package com.gang.exchangesource.demo.logic;

import com.gang.exchangesource.demo.common.ExchangeConnect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * @Classname CreateLogic
 * @Description TODO
 * @Date 2020/3/2 17:28
 * @Created by zengzg
 */
@Component
public class CreateLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExchangeOperationSource exchangeOperationSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        create("", null);

        exchangeOperationSource.createAccount(ExchangeConnect.build(ExchangeHelp.getConfig()),
                ExchangeHelp.getExchangeTO());
        //        exchangeOperationSource.doShell();

        //        Map<String, String> map = new HashMap<>();
        //        map.put(OperationType.ExchangeProName.IDENTITY.getVal(), "1234567");
        //        map.put(OperationType.ExchangeProName.MAILBOX.getVal(), "box");
        //        map.put(OperationType.ExchangeProName.ALIAS.getVal(), "alias");
        //        map.put(OperationType.ExchangeProName.DISPLAYNAME.getVal(), "showname");
        //
        //        create(ObjectClass.MAIL, map);
    }


    //    public void test(ExchangeTO exchangeTO,Ex) {
    //        ExchangeManage manager;
    //        try {
    //            manager = new ExchangeManage();
    //            JSONObject command = new JSONObject();
    //            command.put(ExchangeUtils.getAttrsInfoByBean())
    //            try {
    //                manager.write("json;" + command);
    //            } catch (InterruptedException e) {
    //                e.printStackTrace();
    //            }
    //        } catch (UnknownHostException e) {
    //            // TODO Auto-generated catch block
    //            e.printStackTrace();
    //        } catch (IOException e) {
    //            // TODO Auto-generated catch block
    //            e.printStackTrace();
    //        }
    //        logger.info("------> this is manager <-------");
    //    }
    //
    //    public void create(String cmd, String[] envp) throws IOException {
    //        String command = ExchangeUtils.loadJarResourceFileToString(cmd);
    //        String path = ExchangeUtils.loadJarResourcePathToString(cmd);
    //        StringBuffer buffer = new StringBuffer();
    //        buffer.append(command);
    //
    //        for (String env : envp) {
    //            String[] str = env.split("=");
    //            if (command.indexOf("$" + str[0]) == -1) {
    //                continue;
    //            }
    //            //command = command.replace("$"+str[0], str[1]);
    //            buffer.append(" -" + str[0] + "  " + str[1]);
    //        }
    //        Process proc = null;
    //
    //        try {
    //            logger.info(path + buffer.toString());
    //            //执行脚本文件
    //            proc = Runtime.getRuntime().exec("cmd /c powershell " + path + " " + buffer.toString());
    //            proc.getOutputStream().close();
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        PowerShell powerShell = PowerShell.openSession("powershell.exe");
    //        logger.info(command);
    //        powerShell.executeCommand("add-pssnapin microsoft.exchange*");
    //        // PowerShellResponse response = powerShell.executeCommand(command);
    //        logger.info(path + " " + buffer.toString());
    //        PowerShellResponse response = powerShell.executeCommand(path + " " + buffer.toString());
    //        powerShell.close();
    //    }
    //
    //    public String create(final ObjectClass oc, final Map<String, String> map) {
    //        ExchangeManage conn = null;
    //        if (oc.getVal().equals(ObjectClass.MAIL)) {
    //            String accountName = "";
    //            accountName = getMapValue(map, OperationType.ExchangeProName.IDENTITY.getVal());
    //            String mailbox = getMapValue(map, OperationType.ExchangeProName.MAILBOX.getVal());
    //            String alias = getMapValue(map, OperationType.ExchangeProName.ALIAS.getVal());
    //            String displayName = getMapValue(map, OperationType.ExchangeProName.DISPLAYNAME.getVal());
    //            if (StringUtils.isEmpty(accountName)) {
    //                logger.info("enable mailbox error: identity is null");
    //                throw new IllegalArgumentException("enable mailbox error identity is null");
    //            }
    //            try {
    //                conn = getConnection();
    //                conn.enableAccount(accountName, mailbox, alias, displayName);
    //                //                return new Uid(accountName);
    //            } catch (Exception e) {
    //                // TODO Auto-generated catch block
    //                e.printStackTrace();
    //            } finally {
    //                if (conn != null) {
    //                    try {
    //                        conn.clean();
    //                    } catch (IOException e) {
    //                        // TODO Auto-generated catch block
    //                        e.printStackTrace();
    //                    }
    //                }
    //            }
    //
    //        } else if (oc.getVal().equals(ObjectClass.ADDRESSLIST)) {
    //            String identity = getMapValue(map, OperationType.ExchangeProName.IDENTITY.getVal());
    //            String alias = getMapValue(map, OperationType.ExchangeProName.ALIAS.getVal());
    //            String primarySMTPAddress = getMapValue(map, OperationType.ExchangeProName.PRIMARYSMTPADDRESS
    //            .getVal());
    //            if (StringUtils.isEmpty(identity)) {
    //                logger.info("enable distributiongroup error: param identity is null");
    //                throw new IllegalArgumentException("enable distributiongroup error identity is null");
    //            }
    //            try {
    //                conn = getConnection();
    //                conn.enableDistributionGroup(identity, alias, primarySMTPAddress);
    //                //                return new Uid(identity);
    //            } catch (Exception e) {
    //                // TODO Auto-generated catch block
    //                e.printStackTrace();
    //            } finally {
    //                if (conn != null) {
    //                    try {
    //                        conn.clean();
    //                    } catch (IOException e) {
    //                        // TODO Auto-generated catch block
    //                        e.printStackTrace();
    //                    }
    //                }
    //            }
    //        } else {
    //            logger.info("create mailbox error: objectClass is null");
    //            throw new IllegalArgumentException("Invalid objectclass");
    //        }
    //        return null;
    //    }
    //
    //    public String getMapValue(Map<String, String> map, String key) {
    //        return map.get(key);
    //    }
    //
    //    public ExchangeManage getConnection() {
    //        //            ExchangeManage conn = new ExchangeManage(config.getClientMonitorHost(), config
    //        //            .getClientMonitorPort(),
    //        //                    config.getClientMonitorOuttime(), config.getExchangeAdmin(),
    //        //                    SecurityUtil.decrypt(config.getExchangePassword()), config.getExchangeServiceHost
    //        ());
    //        //            return conn;
    //        return null;
    //    }
}
