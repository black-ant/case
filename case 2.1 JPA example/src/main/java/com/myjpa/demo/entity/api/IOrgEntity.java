package com.myjpa.demo.entity.api;

/**
 * @Classname OrgEntity
 * @Description TODO
 * @Date 2020/8/21 11:04
 * @Created by zengzg
 */
public interface IOrgEntity {
    int getId();

    String getOrgname();

    void setId(int id);

    void setOrgname(String orgname);
}
