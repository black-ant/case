package com.gang.license.demo.to;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>用于获取客户Linux服务器的基本信息</p>

 */
public class LinuxServerInfos {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String[] CPU_SHELL = {"/bin/bash", "-c", "dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1"};
    private final String[] MAIN_BOARD_SHELL = {"/bin/bash", "-c", "dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1"};

    protected String getCPUSerial() throws Exception {
        String result = "";
        String CPU_ID_CMD = "dmidecode";
        BufferedReader bufferedReader = null;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{"sh", "-c", CPU_ID_CMD});// 管道
            bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("uuid");
                if (index >= 0) {// 找到了
                    // 取出mac地址并去除2边空格
                    result = line.substring(index + "uuid".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("获取cpu信息错误", e);
        }
        return result.trim();
//      return GxServerSerialHelper.getLinuxSerial(CPU_SHELL);
    }

    protected String getMainBoardSerial() throws Exception {
        String result = "";
        String maniBord_cmd = "dmidecode | grep 'Serial Number' | awk '{print $3}' | tail -1";
        Process p;
        try {
            p = Runtime.getRuntime().exec(new String[]{"sh", "-c", maniBord_cmd});// 管道
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
                break;
            }
            br.close();
        } catch (IOException e) {
            logger.error("获取主板信息错误", e);
        }
        return result;
//      return GxServerSerialHelper.getLinuxSerial(MAIN_BOARD_SHELL);
    }


}

