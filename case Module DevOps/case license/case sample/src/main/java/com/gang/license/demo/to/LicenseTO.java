package com.gang.license.demo.to;

import java.util.List;

/**
 * @Classname LicenseTO
 * @Description TODO
 * @Date 2021/3/19
 * @Created by zengzg
 */
public class LicenseTO {

    /**
     * MAC 网卡信息
     */
    private List<String> macInfo;

    /**
     * IP 地址信息
     */
    private List<String> ipInfo;

    /**
     * 额外信息
     */
    private List<ExtendsTO> extendsTOS;

    public List<String> getMacInfo() {
        return macInfo;
    }

    public void setMacInfo(List<String> macInfo) {
        this.macInfo = macInfo;
    }

    public List<String> getIpInfo() {
        return ipInfo;
    }

    public void setIpInfo(List<String> ipInfo) {
        this.ipInfo = ipInfo;
    }

    public List<ExtendsTO> getExtendsTOS() {
        return extendsTOS;
    }

    public void setExtendsTOS(List<ExtendsTO> extendsTOS) {
        this.extendsTOS = extendsTOS;
    }
}
