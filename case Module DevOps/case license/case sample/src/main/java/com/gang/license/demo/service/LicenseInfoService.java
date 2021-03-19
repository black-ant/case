package com.gang.license.demo.service;

import com.gang.license.demo.to.LicenseTO;
import com.gang.license.demo.utils.IPUtils;
import com.gang.license.demo.utils.MacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname LicenseInfoService
 * @Description TODO
 * @Date 2021/3/19
 * @Created by zengzg
 */
@Component
public class LicenseInfoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取 LicenseInfo
     *
     * @return
     */
    public LicenseTO getLicenseInfo() {
        return null;
    }

    /**
     * 获取 License 客户端本地信息
     *
     * @return
     */
    public LicenseTO getDefaultServerInfo() throws Exception {
        logger.info("------> [ 获取 本地信息 ] <-------");

        LicenseTO licenseTO = new LicenseTO();
        licenseTO.setIpInfo(IPUtils.getIpAddress());
        licenseTO.setMacInfo(MacUtils.getMacList());

        return licenseTO;
    }


}
