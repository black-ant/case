package com.gang.study.rmi.demo.handler;

import com.gang.study.rmi.demo.service.HelloClient;
import com.gang.study.rmi.demo.service.HelloServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @Classname RunService
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
@Component
public class RunService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HelloClient client;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> [RunService : 远程调用开始] <-------");
//        testOne();
    }


    /**
     * 正常运行流程
     *
     * @throws Exception
     */
    public void testOne() throws Exception {
        testServer();
        testClient();
    }


    public void testServer() throws RemoteException, MalformedURLException, AlreadyBoundException {
        HelloServer server = new HelloServer();
        server.server();
    }


    public void testClient() throws MalformedURLException, RemoteException, NotBoundException {
        client.client();
    }
}
