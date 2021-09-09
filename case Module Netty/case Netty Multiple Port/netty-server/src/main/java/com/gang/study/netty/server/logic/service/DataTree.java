package com.gang.study.netty.server.logic.service;

import com.gang.study.netty.server.to.RequestTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname DataTree
 * @Description TODO
 * @Date 2021/9/7
 * @Created by zengzg
 */
public class DataTree {


    private Map<String, RequestTO> map = new HashMap<>();

    public void addNode(String key, RequestTO value) {
        map.put(key, value);
    }


    public Map<String, RequestTO> getMap() {
        return map;
    }

    public void setMap(Map<String, RequestTO> map) {
        this.map = map;
    }
}
