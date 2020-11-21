package com.gang.study.skywalking.demo.entity;

import java.io.Serializable;

/**
 * @Classname Group
 * @Description TODO
 * @Date 2020/11/21 14:22
 * @Created by zengzg
 */
public class Group implements Serializable {

    private static final long serialVersionUID = 610105280927740076L;

    private String orgUrl;
    private String orgId;

    public Group(String orgUrl, String orgId) {
        this.orgUrl = orgUrl;
        this.orgId = orgId;
    }

    public String getOrgUrl() {
        return orgUrl;
    }

    public void setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
