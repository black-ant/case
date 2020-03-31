package com.gang.azure.oauth.gangazureoauth.entity;

/**
 * @Classname ConfigEntity
 * @Description TODO
 * @Date 2020/3/31 12:03
 * @Created by zengzg
 */
public class ConfigEntity {

    public String client_id = "bdfcdc00-0177-4499-bd8d-98984d2506f7";
    public String response_type = "code";
    public String redirect_uri = "http://localhost:8080/adal4jsample/secure/aad";
    public String response_mode = "query";
    public String resource = "https://303370752qq.onmicrosoft.com/adal4jsample";
    public String state = "12345";

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getResponse_mode() {
        return response_mode;
    }

    public void setResponse_mode(String response_mode) {
        this.response_mode = response_mode;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
