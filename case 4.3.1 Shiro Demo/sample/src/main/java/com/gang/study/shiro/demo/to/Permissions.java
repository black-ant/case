package com.gang.study.shiro.demo.to;

/**
 * @Classname Permissions
 * @Description TODO
 * @Date 2021/3/6 22:32
 * @Created by zengzg
 */
public class Permissions {

    private String id;

    private String permissionsName;

    public Permissions(String id, String permissionsName) {
        this.id = id;
        this.permissionsName = permissionsName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }
}
