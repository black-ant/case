package com.gang.study.rmi.demo.to;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Classname ClientTO
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
public class ClientTO implements Serializable {

    private String userName;

    private Integer userAge;

    private Map<String, String> userParam;

    private List<String> userRole;

    private InnerClientTO clientTO;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Map<String, String> getUserParam() {
        return userParam;
    }

    public void setUserParam(Map<String, String> userParam) {
        this.userParam = userParam;
    }

    public List<String> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<String> userRole) {
        this.userRole = userRole;
    }

    public InnerClientTO getClientTO() {
        return clientTO;
    }

    public void setClientTO(InnerClientTO clientTO) {
        this.clientTO = clientTO;
    }
}
