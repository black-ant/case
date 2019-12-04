package com.gang.study.websocket.module;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname PushMessage
 * @Description TODO
 * @Date 2019/12/3 10:42
 * @Created by zengzg
 */
@Data
public class PushMessage implements Serializable {

    private String gid;

    private String event;

    private String msgType;

    private String content;

    private Integer resultCode = -1;

    public PushMessage() {
    }

    public PushMessage(String gid, String event, String msgType, String content, Integer resultCode) {
        this.gid = gid;
        this.event = event;
        this.msgType = msgType;
        this.content = content;
        this.resultCode = resultCode;
    }

    public PushMessage asResult(MsgError pe) {
        return asResult(pe, null);
    }

    public PushMessage asResult(MsgError pe, String content) {
        this.resultCode = pe.getErrorCode();
        this.content = content == null ? pe.getDesc() : content;
        return this;
    }
}
