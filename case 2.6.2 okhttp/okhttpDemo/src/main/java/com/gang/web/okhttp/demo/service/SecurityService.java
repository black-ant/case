package com.gang.web.okhttp.demo.service;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.security.cert.Certificate;

/**
 * @Classname SecurityService
 * @Description HTTPS
 * @Date 2021/1/10 16:18
 * @Created by zengzg
 */
public class SecurityService {

    private final OkHttpClient client = new OkHttpClient.Builder()
            .certificatePinner(
                    new CertificatePinner.Builder()
                            .add("publicobject.com", "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=")
                            .build())
            .build();

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://publicobject.com/robots.txt")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            for (Certificate certificate : response.handshake().peerCertificates()) {
                System.out.println(CertificatePinner.pin(certificate));
            }
        }
    }
}
