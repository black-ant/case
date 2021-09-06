package com.gang.zookeeper.demo.service;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname ZkClientUtilService
 * @Description TODO
 * @Date 2021/8/9
 * @Created by zengzg
 */
@Component
public class ZkClientUtilService {

    private static final String zkPath = "master:2181,slave1:2181,slave2:2181/zfpt";

//    private static ZkClient zkClient = new ZkClient(zkPath, 10000, 10000);
//
//
//    public void create() {
//        // 创建节点
//        String result = zkClient.create("/aa3", "test", CreateMode.EPHEMERAL);
//        System.out.println(result);
//        // 递归创建
//        zkClient.createPersistent("/trade/open", true);
//        // 注意不要写成这种，API的问题，这种无法递归创建
//        // zkClient.createPersistent("/trade/open",true);
//    }
//
//    public void delete() {
//        // 递归删除
//        Boolean results = zkClient.deleteRecursive("/trade");
//        System.out.println("删除结果:" + results);
//    }
//
//
//    /**
//     * 获取子节点
//     */
//    public void getChildren() {
//        List<String> childrenList = zkClient.getChildren("/trade");
//        System.out.println(childrenList);
//    }
//
//    /**
//     * 获取节点数据
//     */
//    public void readData() {
//        String data = zkClient.readData("/trade");
//        System.out.println(data);
//    }
//
//    /**
//     * 更新节点数据
//     */
//    public void setData() {
//        String oldValue = zkClient.readData("/trade");
//        System.out.println("获取前:" + oldValue);
//        zkClient.writeData("/trade", "I am trade");
//        String newValue = zkClient.readData("/trade");
//        System.out.println("更新后:" + newValue);
//    }


//    /**
//     * 监听器
//     */
//    public void watch() {
//        zkClient.subscribeChildChanges("/trade", (parentPath, currenChilds) -> {
//            System.out.println("子节点发生变化");
//        });
//
//        zkClient.subscribeDataChanges("/trade", new IZkDataListener() {
//            @Override
//            public void handleDataChange(String dataPath, Object data) throws Exception {
//                System.out.println("dataPath:" + dataPath + "发生变化，最新数据是:" + data);
//            }
//
//            @Override
//            public void handleDataDeleted(String dataPath) throws Exception {
//                System.out.printf("dataPath被删除");
//            }
//        });
//    }

}
