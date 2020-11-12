package com.myjpa.demo.entity.api;

/**
 * @Classname IOrgEntity
 * @Description TODO
 * @Date 2020/11/12 16:07
 * @Created by zengzg
 */
public interface IOrgEntity {
    int getId();

    String getOrgName();

    String getOrgType();

    void setId(int id);

    void setOrgName(String orgName);

    void setOrgType(String orgType);
}
