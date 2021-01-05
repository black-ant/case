package com.gang.ipaddress.demo.service;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname IPGegionUtils
 * @Description 官方版
 * @Date 2020/12/18 13:47
 * @Created by zengzg
 */
@Component
public class IPGegionUtils implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        test("memory");
    }

    public void test(String type) {

        String dbFile = "D:\\java\\workspace\\git\\case\\case utils_plugins\\case ip find " +
                "address\\src\\main\\resources\\ip2region.db";
        File file = new File(dbFile);
        if (file.exists() == false) {
            System.out.println("Error: Invalid ip2region.db file");
            return;
        }

        int algorithm = DbSearcher.BTREE_ALGORITHM;
        String algoName = "B-tree";
        if (type.equalsIgnoreCase("binary")) {
            algoName = "Binary";
            algorithm = DbSearcher.BINARY_ALGORITHM;
        } else if (type.equalsIgnoreCase("memory")) {
            algoName = "Memory";
            algorithm = DbSearcher.MEMORY_ALGORITYM;
        }

        try {
            System.out.println("initializing " + algoName + " ... ");
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbFile);

            //define the method
            DataBlock dataBlock = null;
            String line = "59.174.217.84";
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    dataBlock = searcher.btreeSearch(line);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    dataBlock = searcher.binarySearch(line);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
//                    dataBlock = searcher.memorySearch(ipToLong(line));
                    dataBlock = searcher.memorySearch(line);
                    break;
            }

            logger.info("------> data :{} <-------",
                    dataBlock.getRegion() + dataBlock.getCityId() + dataBlock.getDataPtr());
            searcher.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }
    }

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


}
