package com.gang.study.rmi.demo.api;

import com.gang.study.rmi.demo.to.ClientTO;
import com.gang.study.rmi.demo.to.ServerTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @Classname HelloDefine
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
public interface RemoteHandle extends Remote {

    String helloWorld() throws RemoteException;

    String sayHello(String name) throws RemoteException;

    ClientTO remoteTO(ServerTO serverTO) throws RemoteException;

}
