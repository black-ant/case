package com.gang.exchangesource.demo.logic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gang.exchangesource.demo.common.ExchangeConnect;
import com.gang.exchangesource.demo.to.ExchangeTO;
import com.gang.exchangesource.demo.utils.ExchangeUtils;
import com.gang.exchangesource.type.ExchangeType;
import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @Classname ExchangeSource
 * @Description TODO
 * @Date 2020/2/28 16:42
 * @Created by zengzg
 */
@Component
public class ExchangeOperationSource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String createAccount(ExchangeConnect connect, ExchangeTO exchangeTO) {

        logger.info("------> this is in createAccount :{} <-------", JSONObject.toJSONString(exchangeTO));
        String commonStr = "json;" + ExchangeUtils.getCommonStr(connect.getConfig(), exchangeTO,
                ExchangeType.OP_DISABL_EMAIL);

        logger.info("------> this is common :{} <-------", commonStr);
        String back = "";
        // 禁用
        // commonStr = "Disable;wdhacpoc\\administrator;zzg!@19950824;127.0.0.1;gang";

        // 启用
        //        commonStr = "Enable;wdhacpoc\\administrator;zzg!@19950824;127.0.0.1;gang";

        // 创建
        //Enable-DistributionGroup -Identity "CN=testAccount,OU=ExchangeAccount,DC=wdhacpoc,DC=com,DC=cn" -Alias
        // "testAccount" -DisplayName "testAccount" -PrimarySMTPAddress "testAccount@wdhacpoc.com.cn"
        //        commonStr=""

        JSONObject object = ExchangeUtils.getAttrsInfoByBean(connect.getConfig());
        //        object.put("cmdlet", ExchangeType.OP_NEW_EMAIL.getCode());
        //        JSONArray array = new JSONArray();
        //        array.add(ExchangeUtils.getTOItem("Name", "ShellDGroup1"));
        //        array.add(ExchangeUtils.getTOItem("Members", "ShellAccount@wdhacpoc.com.cn"));
        //        object.put("param", array);


        //        object.put("cmdlet", ExchangeType.OP_NEW_USER.getCode());
        //        JSONArray array = new JSONArray();
        //        array.add(ExchangeUtils.getTOItem("Name", "testAccount1"));
        //        array.add(ExchangeUtils.getTOItem("Alias", "testAccount1"));
        //        array.add(ExchangeUtils.getTOItem("FirstName", "testAccount1"));
        //        array.add(ExchangeUtils.getTOItem("UserPrincipalName", "testAccount1@wdhacpoc.com.cn"));
        //        array.add(ExchangeUtils.getTOItem("ExternalEmailAddress", "testAccount@wdhacpoc.com"));
        //        array.add(ExchangeUtils.getTOItem("Password", Sec));
        //        Enable - MailUser - Identity testAccount1 - ExternalEmailAddress john @contoso.com

        object.put("cmdlet", ExchangeType.OP_ENABL_EMAIL.getCode());
        JSONArray array = new JSONArray();
        array.add(ExchangeUtils.getTOItem("Identity", "sync001"));
        object.put("param", array);
        commonStr = "json;" + object.toJSONString();


        try {
            back = connect.doOperation(commonStr);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("E----> this operation is error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
        logger.info("------> this back is :{} <-------", back);
        return null;
    }

    public String doShell() {

        logger.info("------> this is in do shell <-------");

        PowerShell powerShell = PowerShell.openSession("powershell.exe");
        //        powerShell.executeCommand()

        powerShell.executeCommand("Add-PSSnapin Microsoft.Exchange*");

        logger.info("------> this is add 管理单元 <-------");
        PowerShellResponse response1 = powerShell.executeCommand("Get-MailUser");
        logger.info("------> this is response111 :{} <-------", response1.getCommandOutput());

        PowerShellResponse response = powerShell.executeCommand("New-DistributionGroup -Name \"ShellDGroup1\" " +
                "-Members" +
                " ShellAccount@wdhacpoc.com.cn");
        powerShell.close();

        logger.info("------> this is response :{} <-------", response.getCommandOutput());
        return response.getCommandOutput();
    }

}
