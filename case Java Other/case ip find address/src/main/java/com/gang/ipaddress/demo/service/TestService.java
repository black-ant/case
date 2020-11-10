package com.gang.ipaddress.demo.service;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2020/11/9 22:29
 * @Created by zengzg
 */
@Service
public class TestService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String address = IpRegionUtil.getIpCity("59.174.217.84");
        logger.info("------> this is address :{} <-------", address);
        String address2 = getIpCity("59.174.217.84");
        logger.info("------> this is address2 :{} <-------", address2);

    }


    public String getIpCity(String ip) throws Exception {
        String dbPath = resourceLoader.getResource("classpath:ip2region.db").getFile().getPath();
        dbPath = URLDecoder.decode(dbPath, "utf-8");
        File file = new File(dbPath);
        if (!file.exists()) {
            System.out.println("Error: Invalid ip2region.db file");
            return null;
        } else if (!Util.isIpAddress(ip)) {
            System.out.println("Error: Invalid ip address");
            return null;
        }

        logger.info("------> this is getIpCity <-------");
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbPath);
            try {
                searcher.memorySearch(ip);
            } catch (Exception e) {
                e.printStackTrace();
            }

            DataBlock dataBlock = searcher.memorySearch(ipToLong(ip));
            String cityIpString = dataBlock.getRegion();
            String[] splitIpString = cityIpString.split("\\|");
            cityIpString = splitIpString[3];
            return cityIpString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //IP转换为Long
    public static long ipToLong(String ip) {
        String[] ipArray = ip.split("\\.");
        List ipNums = new ArrayList();
        for (int i = 0; i < 4; ++i) {
            ipNums.add(Long.valueOf(Long.parseLong(ipArray[i].trim())));
        }
        long ZhongIPNumTotal = ((Long) ipNums.get(0)).longValue() * 256L * 256L * 256L
                + ((Long) ipNums.get(1)).longValue() * 256L * 256L + ((Long) ipNums.get(2)).longValue() * 256L
                + ((Long) ipNums.get(3)).longValue();

        return ZhongIPNumTotal;
    }

//    public byte[] getByte(String filePath){
//        File file = new File(filePath);
//        FileInputStream in = null;
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        try {
//            in = new FileInputStream(file);
//            byte[] buffer = new byte[in.available()];
//            in.read(buffer);
//            out.write(buffer);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (in != null)
//                    in.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public String getAddress(String ip) {
//        //查询算法
//        //B-tree
//        int algorithm = DbSearcher.BTREE_ALGORITHM;
//        //DbSearcher.BINARY_ALGORITHM //Binary
//        //DbSearcher.MEMORY_ALGORITYM //Memory
//        try {
//            DbConfig config = new DbConfig();
//            DbSearcher searcher = new DbSearcher(config, dbPath);
//            //define the method
//            Method method = null;
//            switch (algorithm) {
//                case DbSearcher.BTREE_ALGORITHM:
//                    method = searcher.getClass().getMethod("btreeSearch", String.class);
//                    break;
//                case DbSearcher.BINARY_ALGORITHM:
//                    method = searcher.getClass().getMethod("binarySearch", String.class);
//                    break;
//                case DbSearcher.MEMORY_ALGORITYM:
//                    method = searcher.getClass().getMethod("memorySearch", String.class);
//                    break;
//            }
//            DataBlock dataBlock = null;
//            if (!Util.isIpAddress(ip)) {
//                System.out.println("Error: Invalid ip address");
//                return null;
//            }
//            dataBlock = (DataBlock) method.invoke(searcher, ip);
//            //（格式：国家|大区|省份|城市|运营商)
//            String cityIpString = dataBlock.getRegion();
//            String[] splitIpString = cityIpString.split("\\|");
//            cityIpString = splitIpString[3];
//            return cityIpString;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
