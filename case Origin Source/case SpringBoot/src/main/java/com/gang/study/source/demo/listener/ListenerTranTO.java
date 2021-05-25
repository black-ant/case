package com.gang.study.source.demo.listener;

import java.util.Map;

/**
 * @Classname ListenerTranTO
 * @Description TODO
 * @Date 2021/5/24
 * @Created by zengzg
 */
public class ListenerTranTO {

    private String eventName;

    private Map<String, String> eventInfo;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Map<String, String> getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(Map<String, String> eventInfo) {
        this.eventInfo = eventInfo;
    }


    @Override
    public String toString() {
        return "ListenerTranTO{" +
                "eventName='" + eventName + '\'' +
                ", eventInfo=" + eventInfo +
                '}';
    }
}
