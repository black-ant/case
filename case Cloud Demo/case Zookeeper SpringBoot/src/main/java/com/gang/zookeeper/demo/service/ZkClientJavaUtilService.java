package com.gang.zookeeper.demo.service;

import com.gang.zookeeper.demo.utils.ZkClientJavaUtil;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Classname ZkClientJavaUtilService
 * @Description TODO
 * @Date 2021/8/9
 * @Created by zengzg
 */
public class ZkClientJavaUtilService {

    /**
     * 同步创建 zk节点
     *
     * @throws Exception
     */
    public void create() throws Exception {
        String response = ZkClientJavaUtil.getZKConnection().create("/aa3", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(response);
    }

    /**
     * 异步回调创建 zk节点
     *
     * @throws Exception
     */
    public void createASync() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //StringCallback 异步回调  ctx:用于传递给回调方法的一个参数。通常是放一个上下文(Context)信息
        ZkClientJavaUtil.getZKConnection().create("/aa2", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, (rc, path, ctx, name) -> {
            System.out.println("rc:" + rc + "&path:" + path + "&ctx:" + ctx + "&name:" + name);
            countDownLatch.countDown();
        }, "1212121");
        countDownLatch.await();
    }

    /**
     * 同步删除
     *
     * @throws Exception
     */
    public void delete() throws Exception {
        // version 表示此次删除针对于的版本号。 传-1 表示不忽略版本号
        ZkClientJavaUtil.getZKConnection().delete("/aa1", -1);
    }

    /**
     * 异步删除
     *
     * @throws Exception
     */
    public void deleteASync() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZkClientJavaUtil.getZKConnection().delete("/aa1", -1, (rc, path, ctx) -> {
            System.out.println("rc:" + rc + "&path:" + path + "&ctx:" + ctx);
            countDownLatch.countDown();
        }, "删除操作");
        countDownLatch.await();
    }

    /**
     * 同步获取数据，包括子节点列表的获取和当前节点数据的获取
     *
     * @throws Exception
     */
    public void getChildren() throws Exception {
        Stat stat = new Stat();
        // path:指定数据节点的节点路径， 即API调用的目的是获取该节点的子节点列表
        // Watcher : 注册的Watcher。一旦在本次获取子节点之后，子节点列表发生变更的话，就会向该Watcher发送通知。Watcher仅会被触发一次。
        // state: 获取指定数据节点(也就是path参数对应的节点)的状态信息(无节点名和数据内容)，传入旧的state将会被来自服务端响应的新state对象替换。
        List<String> list = ZkClientJavaUtil.getZKConnection().getChildren("/", event -> {
            System.out.println("我是监听事件，监听子节点变化");
        }, stat);
        System.out.println(list);
        System.out.println(stat);
    }

    /**
     * 异步获取子节点
     *
     * @throws Exception
     */
    public void getChildrenASync() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZkClientJavaUtil.getZKConnection().getChildren("/", event -> {
            System.out.println("我是监听事件，监听子节点变化");
        }, (rc, path, ctx, children) -> {
            //异步回调
            System.out.println("children:" + children);
            countDownLatch.countDown();
        }, "context");
        countDownLatch.await();
    }

    /**
     * 同步获取数据
     *
     * @throws Exception
     */
    public void getDataTest() throws Exception {
        Stat stat = new Stat();
        byte[] bytes = ZkClientJavaUtil.getZKConnection().getData("/aa1", event -> {
            System.out.println("我是监听事件，监听数据状态发生变化");
        }, stat);
        System.out.println(new String(bytes));
    }

    public void getDataASync() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZkClientJavaUtil.getZKConnection().getData("/aa1", event -> {
            System.out.println("我是监听事件，监听数据状态发生变化");
        }, (rc, path, ctx, data, stat) -> {
            System.out.println("获取到的内容是：" + new String(data));
            countDownLatch.countDown();
        }, "121");
        countDownLatch.await();
    }

    /**
     * 同步更新数据
     */
    public void setData() throws Exception {
        byte[] oldValue = ZkClientJavaUtil.getZKConnection().getData("/aa1", false, null);
        System.out.println("更新前值是:" + new String(oldValue));
        Stat stat = ZkClientJavaUtil.getZKConnection().setData("/aa1", "helloWorld".getBytes(), -1);
        byte[] newValue = ZkClientJavaUtil.getZKConnection().getData("/aa1", false, null);
        System.out.println("更新后值是:" + new String(newValue));
    }

    /**
     * 异步更新数据
     *
     * @throws Exception
     */
    public void setDataASync() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZkClientJavaUtil.getZKConnection().setData("/aa1", "helloChina".getBytes(), -1, (rc, path, ctx, name) -> {
            System.out.println("更新成功");
            countDownLatch.countDown();
        }, "1111");
        countDownLatch.await();
        byte[] newValue = ZkClientJavaUtil.getZKConnection().getData("/aa1", false, null);
        System.out.println("更新前值是:" + new String(newValue));
    }
}
