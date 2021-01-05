package com.gang.systeminfo.demo.controller;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * @Classname ComputerInfo
 * @Description TODO
 * @Date 2020/10/13 10:13
 * @Created by zengzg
 */
@Component
public class ComputerInfoService implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Enumeration<NetworkInterface> en = NetworkInterface
                .getNetworkInterfaces();
        while (en.hasMoreElements()) {
            printParameter(en.nextElement());
        }
    }

    public static void printParameter(NetworkInterface ni)
            throws SocketException {
        System.out.println(" Name = " + ni.getName());
        System.out.println(" Display Name = " + ni.getDisplayName());
        System.out.println(" Is up = " + ni.isUp());
        System.out.println(" Support multicast = " + ni.supportsMulticast());
        System.out.println(" Is loopback = " + ni.isLoopback());
        System.out.println(" Is virtual = " + ni.isVirtual());
        System.out.println(" Is point to point = " + ni.isPointToPoint());
        byte[] macaddress = ni.getHardwareAddress();
        if (macaddress != null) {
            System.out.printf(
                    " Hardware address = %02X-%02X-%02X-%02X-%02X-%02X\n",
                    macaddress[0], macaddress[1], macaddress[2], macaddress[3],
                    macaddress[4], macaddress[5]);
        } else {
            System.out.println(" Hardware address = null");
        }
        System.out.println(" MTU = " + ni.getMTU());
        System.out.println("\n List of Interface Addresses:");
        List<InterfaceAddress> list = ni.getInterfaceAddresses();
        Iterator<InterfaceAddress> it = list.iterator();
        while (it.hasNext()) {
            InterfaceAddress ia = it.next();
            System.out.println(" Address = " + ia.getAddress());
            System.out.println(" Broadcast = " + ia.getBroadcast());
            System.out.println(" Network prefix length = "
                    + ia.getNetworkPrefixLength());
            System.out.println("");
        }
    }
}
