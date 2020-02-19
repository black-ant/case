package com.gang.study.adtest.demo.to;

import lombok.Data;

/**
 * @Classname ADConfigInfo
 * @Description TODO
 * @Date 2019/12/5 20:01
 * @Created by zengzg
 */
@Data
public class ADConfigInfoTO {

    private String host;

    private String port;

    private String baseContext;

    private String account;

    private String password;

    private String uidAttribute = "objectGUID";

    private String gidAttribute = "objectGUID";

    private String oidAttribute = "objectGUID";

    private Boolean isSSL = Boolean.TRUE;

    @Override
    public String toString() {
        return "ADConfigInfoTO{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", baseContext='" + baseContext + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", uidAttribute='" + uidAttribute + '\'' +
                ", gidAttribute='" + gidAttribute + '\'' +
                ", oidAttribute='" + oidAttribute + '\'' +
                ", isSSL=" + isSSL +
                '}';
    }
}
