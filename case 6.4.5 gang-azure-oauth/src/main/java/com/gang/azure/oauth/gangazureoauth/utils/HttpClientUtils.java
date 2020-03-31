package com.gang.azure.oauth.gangazureoauth.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Classname HttpClientUtils
 * @Description TODO
 * @Date 2019/11/15 11:45
 * @Created by zengzg
 */

public final class HttpClientUtils {

    private static Logger LOG = LoggerFactory.getLogger(HttpClientUtils.class);

    // 请求超时时间
    private static int SOCKET_TIMEOUT = 300000;
    // 传输超时时间
    private static int CONNECT_TIMEOUT = 300000;

    public static final String CHARSET = "UTF-8";

    public static String sendHttpPost(String url, String body) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setEntity(new StringEntity(body, Charset.forName("UTF-8")));
        CloseableHttpResponse httpResponse = null;
        // 返回值内容
        String content = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                LOG.info("Response status:" + httpResponse.getStatusLine());
                content = EntityUtils.toString(entity, "utf-8");
                LOG.info("Response content:" + content);
                LOG.info("Content length:" + content.length());
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOG.info("Error exception:" + e.getMessage());
            e.printStackTrace();

        } finally {
            if (httpResponse != null) {
                close(httpResponse);
            }
            close(httpClient);
        }

        return content;

    }

    private HttpClientUtils() {

    }

    public void test() {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet("http://www.baidu.com");
            CloseableHttpResponse response = httpClient.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String strResult = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //   httpGet.setHeader("Content-Type", "application/json;charset=utf-8");
    public static String doGet(String url, Map<String, String> headerMap) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("Content-Type", "application/json;charset=utf-8");
        headerMap.keySet().forEach(item -> {
            httpGet.setHeader(item, headerMap.get(item));
        });

        CloseableHttpResponse httpResponse = null;
        // 返回值内容
        String content = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                LOG.info("Response status:" + httpResponse.getStatusLine());
                content = EntityUtils.toString(entity, "utf-8");
                LOG.info("Response content:" + content);
                LOG.info("Content length:" + content.length());
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOG.info("Error exception:" + e.getMessage());
            e.printStackTrace();

        } finally {
            if (httpResponse != null) {
                close(httpResponse);
            }
            close(httpClient);
        }

        return content;
    }

    private static <T extends java.io.Closeable> void close(T t) {
        try {
            if (t != null) {
                t.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 无需SSL 证书 Post 请求
     * 如果调用 https 接口的时候出现了例如 SSLExeption 类报错 , 可能是缺少 SSL 证书的原因
     * 此时 , 需要配置 SSL 证书 ,或者调用该接口
     *
     * @param postUrl
     * @param paramJson
     * @param token
     * @return
     */
    public static String doPostNoSSL(String postUrl, String paramJson, Map<String, String> headerMap) {
        String resultStr = ""; // 返回结果
        try {

            //            1、创建httpClient
            //            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpClient buildSSLCloseableHttpClient = buildSSLCloseableHttpClient();

            System.setProperty("jsse.enableSNIExtension", "false");
            HttpPost httpPost = new HttpPost(postUrl);

            if (headerMap != null && headerMap.get("token") != null) {
                httpPost.setHeader("Authorization", "Bearer " + headerMap.get("token"));
            }
            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT).build();

            httpPost.setConfig(requestConfig);

            if (headerMap != null && headerMap.get("Content-Type") != null) {
                httpPost.setHeader("Content-Type", headerMap.get("Content-Type"));
            } else {
                httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            }


            // 放入请求参数
            StringEntity data = new StringEntity(paramJson, Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            // 发送请求，接收结果
            CloseableHttpResponse response = buildSSLCloseableHttpClient.execute(httpPost);

            // 4.获取响应对象中的响应码
            StatusLine statusLine = response.getStatusLine();
            int responseCode = statusLine.getStatusCode();

            System.out.println(responseCode);

            if (responseCode == 200) {
                // 打印响应内容
                resultStr = EntityUtils.toString(response.getEntity(), "UTF-8");

                // 5. 可以接收和发送消息
                HttpEntity entity = response.getEntity();
                // 6.从消息载体对象中获取操作的读取流对象
                InputStream input = entity.getContent();

            } else {
                System.out.println("响应失败! : " + response.toString());
            }
            buildSSLCloseableHttpClient.close();

        } catch (Exception e) {
            LOG.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }

        return resultStr;
    }


    public static String doPost(String url, HashMap<String, String> map) throws Exception {
        String result = "";
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(550000).setConnectTimeout(550000)
                .setConnectionRequestTimeout(550000).setStaleConnectionCheckEnabled(true).build();
        client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        // client = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(url);
        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("Charset", CHARSET);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            params.add(pair);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        try {
            response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, CHARSET);
                }
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException("创建连接失败" + e);
        } catch (IOException e) {
            throw new RuntimeException("创建连接失败" + e);
        }

        return result;
    }

    /**
     * 构建无需 Https SSL 证书 的 Client
     *
     * @return
     * @throws Exception
     */
    private static CloseableHttpClient buildSSLCloseableHttpClient() throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 信任所有
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        // ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

}
