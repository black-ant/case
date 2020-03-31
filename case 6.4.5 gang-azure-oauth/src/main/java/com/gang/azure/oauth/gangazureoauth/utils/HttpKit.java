package com.gang.azure.oauth.gangazureoauth.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.HttpMethod;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @Classname HttpKit
 * @Description TODO
 * @Date 2019/11/15 11:35
 * @Created by zengzg
 */

public final class HttpKit {

    public static final String CHARSET = "UTF-8";

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpKit.class);

    public static String get(String url) {
        return get(url, CHARSET);
    }

    public static String get(String url, String charset) {
        return get(url, null, null, charset);
    }

    public static String get(String url, Map<String, Object> queryParas) {
        return get(url, queryParas, null, CHARSET);
    }

    public static String get(String url, Map<String, Object> queryParas, String charset) {
        return get(url, queryParas, null, charset);
    }

    public static String get(String url, Map<String, Object> queryParas, Map<String, String> headers) {
        return get(url, queryParas, headers, CHARSET);
    }

    public static String get(String url, Map<String, Object> queryParas, Map<String, String> headers, String charset) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas, charset), HttpMethod.GET, headers);
            conn.setConnectTimeout(10000000);
            conn.connect();
            return readResponseString(conn, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String post(String url, Map<String, Object> queryParas, String data, Map<String, String> headers,
                              String charset) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas, charset), HttpMethod.POST, headers);
            conn.connect();
            OutputStream out = conn.getOutputStream();
            if (null != data) {
                out.write(data.getBytes(Charset.forName(charset)));
            }
            out.flush();
            out.close();

            return readResponseString(conn, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String postJson(String url, Map<String, Object> queryParas, String data, Map<String, String> headers,
                                  String charset) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnectionJson(buildUrlWithQueryString(url, queryParas, charset), HttpMethod.POST, headers);
            conn.connect();
            OutputStream out = conn.getOutputStream();
            if (null != data) {
                out.write(data.getBytes(Charset.forName(charset)));
            }
            out.flush();
            out.close();

            return readResponseString(conn, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String post(String url, Map<String, Object> queryParas, String data, Map<String, String> headers) {
        return post(url, queryParas, data, headers, CHARSET);
    }

    public static String post(String url, Map<String, Object> queryParas, String data) {
        return post(url, queryParas, data, null, CHARSET);
    }

    public static String postJson(String url, Map<String, Object> queryParas, String data) {
        return postJson(url, queryParas, data, null, CHARSET);
    }

    public static String post(String url, Map<String, Object> queryParas, String data, String charset) {
        return post(url, queryParas, data, null, charset);
    }

    public static String post(String url, String data, Map<String, String> headers) {
        return post(url, null, data, headers);
    }

    public static String post(String url, String data) {
        return post(url, null, data, null, CHARSET);
    }

    private static String readResponseString(HttpURLConnection conn, String charset) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(charset)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers)
            throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        URL openUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) openUrl.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(SSLSOCKETFACTORY);
            ((HttpsURLConnection) conn).setHostnameVerifier(TRUSTANYHOSTNAMEVERIFIER);
        }

        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setConnectTimeout(900000000);
        conn.setReadTimeout(900000000);

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 " +
                        "Safari/537.36");

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }

    private static HttpURLConnection getHttpConnectionJson(String url, String method, Map<String, String> headers)
            throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        URL openUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) openUrl.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(SSLSOCKETFACTORY);
            ((HttpsURLConnection) conn).setHostnameVerifier(TRUSTANYHOSTNAMEVERIFIER);
        }

        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setConnectTimeout(900000000);
        conn.setReadTimeout(900000000);

        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 " +
                        "Safari/537.36");

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return conn;
    }

    private static String buildUrlWithQueryString(String url, Map<String, Object> queryParas, String charset) {
        if (queryParas == null || queryParas.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        if (url.indexOf("?") == -1) {
            isFirst = true;
            sb.append("?");
        } else {
            isFirst = false;
        }

        for (Map.Entry<String, Object> entry : queryParas.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }
            String key = entry.getKey();
            Object value = entry.getValue();
            if (null != value) {
                try {
                    value = URLEncoder.encode(value.toString(), charset);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            sb.append(key).append("=").append(value);
        }
        LOGGER.debug("http请求参数地址：" + sb);
        return sb.toString();
    }

    private class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private class TrustAnyTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private static final SSLSocketFactory SSLSOCKETFACTORY = initSSLSocketFactory();
    private static final TrustAnyHostnameVerifier TRUSTANYHOSTNAMEVERIFIER =
            new HttpKit().new TrustAnyHostnameVerifier();

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] tm = {new HttpKit().new TrustAnyTrustManager()};

            SSLContext sslContext = SSLContext.getInstance("TLS"); // ("TLS",
            // "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpKit() {

    }

}
