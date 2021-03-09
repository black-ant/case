package com.gang.cloud.template.demo.entity;


//@Entity
//@Table(name = "user")
public class CloudTemplateEntity implements com.gang.cloud.template.demo.entity.api.ICloudTemplateEntity {

    //    @Id
    //    @Basic(optional = false)
    //    @NotNull
    private int userid;

    //    @Column(name = "username")
    private String username;

    //    @Column(name = "desc")
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
