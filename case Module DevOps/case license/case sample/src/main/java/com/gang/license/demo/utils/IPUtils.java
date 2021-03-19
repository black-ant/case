package com.gang.license.demo.utils;

import org.springframework.util.CollectionUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname IPUtils
 * @Description TODO
 * @Date 2021/3/19
 * @Created by zengzg
 */
public class IPUtils {

    /**
     * 获取 IP 地址
     *
     * @return
     * @throws Exception
     */
    public static List<String> getIpAddress() throws Exception {
        List<InetAddress> inetAddresses = getLocalAllInetAddress();
        if (!CollectionUtils.isEmpty(inetAddresses)) {
            return inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * @return
     * @throws Exception
     */
    private static List<InetAddress> getLocalAllInetAddress() throws Exception {
        List<InetAddress> result = new ArrayList<>(4);
        // 遍历所有的网络接口
        for (Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
            // 在所有的接口下再遍历IP
            for (Enumeration addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                InetAddress address = (InetAddress) addresses.nextElement();
                //排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                if (!address.isLoopbackAddress() /*&& !inetAddr.isSiteLocalAddress()*/
                        && !address.isLinkLocalAddress() && !address.isMulticastAddress()) {
                    result.add(address);
                }
            }
        }
        return result;
    }
}
