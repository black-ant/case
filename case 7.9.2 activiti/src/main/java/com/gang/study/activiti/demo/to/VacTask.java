package com.gang.study.activiti.demo.to;

import java.util.Date;

/**
 * @Classname VacTask
 * @Description TODO
 * @Date 2021/2/11 14:30
 * @Created by zengzg
 */
public class VacTask {

    private String id;
    private String name;
    private Vacation vac;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vacation getVac() {
        return vac;
    }

    public void setVac(Vacation vac) {
        this.vac = vac;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
