package com.gang.study.rmi.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.rmi.demo.api.RemoteHandle;
import com.gang.study.rmi.demo.to.InnerServerTO;
import com.gang.study.rmi.demo.to.ServerTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname HelloClient
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
@Component
public class HelloClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RemoteHandle handle;

    public void client() throws MalformedURLException, RemoteException, NotBoundException {

        logger.info("------> client 调用开始  <-------");
        handle = (RemoteHandle) Naming.lookup("rmi://localhost:8888/Hello");

        ServerTO serverTO = new ServerTO();
        serverTO.setUserName("serverName");
        serverTO.setUserAge(1);
        serverTO.setUserRole(Arrays.asList("1", "2"));

        Map map = new HashMap<>();
        map.put("1", "1");
        serverTO.setUserParam(map);

        InnerServerTO serverTO1 = new InnerServerTO();
        serverTO1.setUserName("innerServer");
        serverTO.setServerTO(serverTO1);

        logger.info("------> 请求 Client helloWorld 返回 :{} <-------", handle.helloWorld());
        logger.info("------> 请求 Client sayHello 返回 :{} <-------", handle.sayHello("China"));
        logger.info("------> 请求 Client remoteTO 返回 :{} <-------", JSONObject.toJSONString(handle.remoteTO(serverTO)));

    }

}
