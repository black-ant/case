package com.gang.study.adsource.demo.common;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @Classname TrustAllTrustManager
 * @Description TODO
 * @Date 2020/2/19 13:58
 * @Created by zengzg
 */
public class TrustAllTrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] xcs, String string)
            throws CertificateException {
        // do nothing
    }

    @Override
    public void checkServerTrusted(X509Certificate[] xcs, String string)
            throws CertificateException {
        // do nothing
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
    }
}

