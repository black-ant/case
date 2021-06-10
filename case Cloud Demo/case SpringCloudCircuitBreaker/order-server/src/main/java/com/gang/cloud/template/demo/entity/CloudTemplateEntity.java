package com.gang.cloud.template.demo.entity;


public class CloudTemplateEntity implements com.gang.cloud.template.demo.entity.api.ICloudTemplateEntity {

    private int userid;

    private String username;

    private String desc;

    @Override
    public int getUserid() {
        return userid;
    }

    @Override
    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
