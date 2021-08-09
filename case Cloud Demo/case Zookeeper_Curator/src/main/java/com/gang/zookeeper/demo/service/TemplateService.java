package com.gang.zookeeper.demo.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Classname TemplateService
 * @Description TODO
 * @Date 2021/8/9
 * @Created Thank's by https://www.zybuluo.com/boothsun/note/990793
 */
public class TemplateService {

    private CuratorFramework curatorFramework;

    private CuratorZookeeperClient client;

    /**
     * 初始化逻辑
     *
     * @return
     */
    public CuratorFramework init() {
        //  非Fluent风格
        //  CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zkPath, new RetryOneTime(100));
        //  System.out.println(curatorFramework.getState());
        //  curatorFramework.start();
        //  System.out.println(curatorFramework.getState());
        // Fluent风格
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("master:2181,slave1:2181,slave2:2181")
                .retryPolicy(new RetryOneTime(1000)) //重试策略
                .namespace("zfpt") // 命名空间
                .build();
        curatorFramework.start();
        return curatorFramework;
    }


    public void create() throws Exception {
        // 创建一个持久化节点，初始化内容为空
        curatorFramework.create().forPath("/dus");
        // 创建一个持久化节点，初始化内容不为空
        curatorFramework.create().forPath("/dus1", "test".getBytes());
        // 创建一个临时节点  初始化内容为空
        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/dus2");
        // 创建一个临时节点，并递归创建不存在的父节点
        // ZooKeeper中规定所有非叶子节点必须为持久节点。因此下面创建出来只有dus2会是临时节点。
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/dj/dus2");
    }


    public void delete(String path, int version) throws Exception {
        //删除一个节点
        curatorFramework.delete().forPath(path);
        // 删除一个节点，并递归删除其所有子节点
        curatorFramework.delete().deletingChildrenIfNeeded().forPath(path);
        // 删除一个节点，强制指定版本进行删除
        curatorFramework.delete().withVersion(version).forPath(path);
        //删除一个节点，强制保证删除成功
        curatorFramework.delete().guaranteed().forPath(path);
    }


    /**
     * 异步接口
     *
     * @throws Exception
     */
    public void BackgroundCallbackTest() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        curatorFramework.getData().inBackground((client, event) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(event);
            System.out.println(client);
        }).forPath("/trade");
        Executor executor = Executors.newFixedThreadPool(2, new ThreadFactoryBuilder().setNameFormat("curator-%d").build());
        curatorFramework.getData().inBackground((client, event) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(event);
            System.out.println(client);
        }, executor).forPath("/trade");
        countDownLatch.await();
    }

    /**
     * 事件监听
     *
     * @throws Exception
     */
    public void NodeCacheTest() throws Exception {
        // client : Curator 客户端实例 。 path: 监听节点的节点路径 。 dataIsCompressed：是否进行数据压缩
        NodeCache nodeCache = new NodeCache(curatorFramework, "/trade", false);
        // buildInitial：如果设置为true 则NodeCache在第一次启动的时候就会立刻从ZK上读取对应节点的数据内容 保存到Cache中。
        nodeCache.start(false);
        nodeCache.getListenable().addListener(() -> {
            System.out.println("Node data update , new data:" + new String(nodeCache.getCurrentData().getData()));
        });
        //******************** 监听一个不存在的节点 当节点被创建后，也会触发监听器 **********************//
        // client : Curator 客户端实例 。 path: 监听节点的节点路径 。 dataIsCompressed：是否进行数据压缩
        NodeCache nodeCache2 = new NodeCache(curatorFramework, "/trade1", false);
        // buildInitial：如果设置为true 则NodeCache在第一次启动的时候就会立刻从ZK上读取对应节点的数据内容 保存到Cache中。
        nodeCache2.start(false);
        nodeCache2.getListenable().addListener(() -> {
            System.out.println("Node data update , new data:" + new String(nodeCache.getCurrentData().getData()));
        });
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 子节点缓存
     *
     * @throws Exception
     */
    public void PathChildrenCacheTest() throws Exception {
        PathChildrenCache nodeCache = new PathChildrenCache(curatorFramework, "/trade", true);
        // buildInitial：如果设置为true 则NodeCache在第一次启动的时候就会立刻从ZK上读取对应节点的数据内容 保存到Cache中。
        nodeCache.start();
        nodeCache.getListenable().addListener((client, event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    System.out.println("新增子节点,数据内容是" + new String(event.getData().getData()));
                    break;
                case CHILD_UPDATED:
                    System.out.println("子节点被更新,数据内容是" + new String(event.getData().getData()));
                    break;
                case CHILD_REMOVED:
                    System.out.println("删除子节点,数据内容是" + new String(event.getData().getData()));
                    break;
                default:
                    break;
            }
        });
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/trade/PathChildrenCache", "new".getBytes());
        Thread.sleep(100L);
        curatorFramework.setData().forPath("/trade/PathChildrenCache", "update".getBytes());
        Thread.sleep(100L);
        curatorFramework.delete().withVersion(-1).forPath("/trade/PathChildrenCache");
    }


    /**
     * Leader 选举
     *
     * @throws Exception
     */
    public void leaderSelector() throws Exception {
        AtomicInteger masterCount = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(4, new ThreadFactoryBuilder().setNameFormat("master_selector-%d").build());
        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                LeaderSelector leaderSelector = new LeaderSelector(curatorFramework, "/master_selector", new LeaderSelectorListenerAdapter() {
                    @Override
                    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                        masterCount.incrementAndGet();
                        System.out.println(Thread.currentThread().getName() + "成为Master, 当前Master数量：" + masterCount);
                        Thread.sleep(1000L);
                        System.out.println(Thread.currentThread().getName() + "宕机，失去Master角色，剩下master数量：" + masterCount.decrementAndGet());
                    }
                });
                leaderSelector.autoRequeue();
                leaderSelector.start();
            });
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * @throws Exception
     */
    public void InterProcessMutex() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, "/trade/mylock");
        for (int i = 0; i < 100; i++) {
            Thread currentThread = new Thread(() -> {
                try {
                    // 加锁
                    lock.acquire();
                    System.out.println(Thread.currentThread().getName() + " 抢到锁");
                } catch (Exception e) {
                } finally {
                    try {
                        System.out.println(Thread.currentThread().getName() + " 释放锁");
                        // 释放锁
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            currentThread.setName("Lock【" + i + "】");
            currentThread.start();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 分布式计数器
     *
     * @throws Exception
     */
    public void DistributedAtomicInteger() throws Exception {
        DistributedAtomicInteger distributedAtomicInteger = new DistributedAtomicInteger(curatorFramework, "/trade/PathChildrenCache", new RetryNTimes(1000, 3));
        System.out.println(distributedAtomicInteger.increment().postValue());
    }

    /**
     * barrier
     *
     * @throws Exception
     */
    public void barrier() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(4, new ThreadFactoryBuilder().setNameFormat("barrier-%d").build());

        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                CuratorFramework client = CuratorFrameworkFactory.builder()
                        .connectString("master:2181,slave1:2181,slave2:2181")
                        .retryPolicy(new RetryOneTime(1000)) //重试策略
                        .namespace("zfpt") // 命名空间
                        .build();
                client.start();
                DistributedBarrier distributedBarrier = new DistributedBarrier(curatorFramework, "/trade/PathChildrenCache");
                System.out.println(Thread.currentThread().getName() + "到达Barrier前");
                try {
                    distributedBarrier.setBarrier();
                    distributedBarrier.waitOnBarrier();
                    System.out.println(Thread.currentThread().getName() + "越过屏障");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(3000L);
                    distributedBarrier.removeBarrier();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

    /**
     * 定义成员数量，到齐了就 越过屏障
     *
     * @throws Exception
     */
    public void barrier2() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(4, new ThreadFactoryBuilder().setNameFormat("barrier-%d").build());
        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                CuratorFramework client = CuratorFrameworkFactory.builder()
                        .connectString("master:2181,slave1:2181,slave2:2181")
                        .retryPolicy(new RetryOneTime(1000)) //重试策略
                        .namespace("zfpt") // 命名空间
                        .build();
                client.start();
                DistributedDoubleBarrier distributedDoubleBarrier = new DistributedDoubleBarrier(client, "/trade/PathChildrenCache", 4);
                try {
                    Thread.sleep(1000L);
                    System.out.println(Thread.currentThread().getName() + "到达Barrier前");
                    distributedDoubleBarrier.enter();
                    System.out.println(Thread.currentThread().getName() + "越过屏障");
                    Thread.sleep(1000L);
                    distributedDoubleBarrier.leave();
                    System.out.println(Thread.currentThread().getName() + "已经离开");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

}
