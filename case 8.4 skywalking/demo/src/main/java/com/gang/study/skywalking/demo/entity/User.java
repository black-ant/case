package com.gang.study.skywalking.demo.entity;


import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @Classname Ticket
 * @Description TODO
 * @Date 2020/11/21 14:21
 * @Created by zengzg
 */
public class User implements Serializable {

    private static final long serialVersionUID = -4223319704861765405L;

    private Boolean expired;
    private Org org;
    private HashMap<String, Org> OrgMap;
    private Group group;
    private Set<Group> groupSet;
    private HashSet<String> hashSet;


    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public HashMap<String, Org> getOrgMap() {
        return OrgMap;
    }

    public void setOrgMap(HashMap<String, Org> orgMap) {
        OrgMap = orgMap;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Set<Group> getGroupSet() {
        return groupSet;
    }

    public void setGroupSet(Set<Group> groupSet) {
        this.groupSet = groupSet;
    }

    public HashSet<String> getHashSet() {
        return hashSet;
    }

    public void setHashSet(HashSet<String> hashSet) {
        this.hashSet = hashSet;
    }
}
