package com.gang.cloud.template.to;

import java.io.Serializable;

/**
 * @Classname CommonResponseTO
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
public class CommonResponseTO implements Serializable {

    private String msg;

    private Boolean status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
