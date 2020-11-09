package com.gang.ipaddress.demo.service;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URLDecoder;

/**
 * @Classname IpUtils
 * @Description TODO
 * @Date 2020/11/9 22:29
 * @Created by zengzg
 */
public class IpRegionUtil {

    private static Logger logger = LoggerFactory.getLogger(IpRegionUtil.class);

    public static String getIpCity(String ip) throws Exception {
        //db，读取resources目录下的ip2region.db
        String dbPath = IpRegionUtil.class.getResource("/ip2region.db").getPath();
        dbPath = URLDecoder.decode(dbPath, "utf-8");
        File file = new File(dbPath);
        if (!file.exists()) {
            System.out.println("Error: Invalid ip2region.db file");
            return null;
        }
        logger.info("------> this is getIpCity <-------");
        //查询算法
        //B-tree
        int algorithm = DbSearcher.BTREE_ALGORITHM;
        //DbSearcher.BINARY_ALGORITHM //Binary
        //DbSearcher.MEMORY_ALGORITYM //Memory
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbPath);
            //define the method
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }
            DataBlock dataBlock = null;
            if (!Util.isIpAddress(ip)) {
                System.out.println("Error: Invalid ip address");
                return null;
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            //（格式：国家|大区|省份|城市|运营商)
            String cityIpString = dataBlock.getRegion();
            String[] splitIpString = cityIpString.split("\\|");
            cityIpString = splitIpString[3];
            return cityIpString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取jar包中的资源文件
     *
     * @param fileName 文件名
     * @return 文件内容
     * @throws IOException 读取错误
     */
    private String readJarFile(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));

        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}
