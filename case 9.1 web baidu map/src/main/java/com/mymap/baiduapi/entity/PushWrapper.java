package com.mymap.baiduapi.entity;

import java.io.Serializable;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/28 20:17
 * @Version 1.0
 **/
public class PushWrapper implements Serializable {

    private String groupid;
    private String pushtype;
    private String msgtype;
    private String msg;
    private String datacontent;
    private Integer pushcode;

    public PushWrapper wrapper(PushError error, String content) {
        this.pushcode = error.getPsuhCode();
        this.datacontent = content == null ? error.getMsg() : content;
        return this;
    }

    public PushWrapper wrapper(PushError error) {
        return wrapper(error, null);
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getPushtype() {
        return pushtype;
    }

    public void setPushtype(String pushtype) {
        this.pushtype = pushtype;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDatacontent() {
        return datacontent;
    }

    public void setDatacontent(String datacontent) {
        this.datacontent = datacontent;
    }

    public Integer getPushcode() {
        return pushcode;
    }

    public void setPushcode(Integer pushcode) {
        this.pushcode = pushcode;
    }


    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    @Override
    public String toString() {
        return "PushWrapper{" +
                "groupid='" + groupid + '\'' +
                ", pushtype='" + pushtype + '\'' +
                ", msgtype='" + msgtype + '\'' +
                ", msg='" + msg + '\'' +
                ", datacontent='" + datacontent + '\'' +
                ", pushcode=" + pushcode +
                '}';
    }
}
