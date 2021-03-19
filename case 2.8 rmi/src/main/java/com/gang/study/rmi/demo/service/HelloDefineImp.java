package com.gang.study.rmi.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.rmi.demo.api.RemoteHandle;
import com.gang.study.rmi.demo.to.ClientTO;
import com.gang.study.rmi.demo.to.ServerTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @Classname HelloDefineImp
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
public class HelloDefineImp extends UnicastRemoteObject implements RemoteHandle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final long serialVersionUID = 1L;

    public HelloDefineImp() throws RemoteException {
        super();
    }

    public String helloWorld() throws RemoteException {
        logger.info("------> [Client 端接收到第一个请求] <-------");
        return "Hello World !";
    }

    public String sayHello(String name) throws RemoteException {
        logger.info("------> [Client 端接收到参数请求 :{}] <-------", name);
        return "Hello" + name + "!";
    }

    @Override
    public ClientTO remoteTO(ServerTO serverTO) throws RemoteException {

        logger.info("------> [Client 端接收到 TO :{}] <-------", JSONObject.toJSONString(serverTO));
        serverTO.setUserName("Client Username");
        ClientTO clientTO = new ClientTO();
        BeanUtils.copyProperties(serverTO, clientTO);
        return clientTO;
    }

}
