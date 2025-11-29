# Thread Module

## 项目说明

Java 多线程和并发编程示例集合。

## 技术栈

- Java 11
- Spring Boot 2.7.x
- JUC (java.util.concurrent)

## 核心内容

### 线程基础

| 主题 | 说明 |
|------|------|
| Thread/Runnable | 创建线程 |
| Callable/Future | 带返回值的任务 |
| 线程状态 | NEW, RUNNABLE, BLOCKED, WAITING, TERMINATED |
| 线程同步 | synchronized, Lock |

### 线程池

| 类型 | 说明 |
|------|------|
| FixedThreadPool | 固定大小线程池 |
| CachedThreadPool | 缓存线程池 |
| ScheduledThreadPool | 定时任务线程池 |
| SingleThreadExecutor | 单线程执行器 |
| ForkJoinPool | 分治任务线程池 |

### 并发工具

| 工具 | 说明 |
|------|------|
| CountDownLatch | 计数器 |
| CyclicBarrier | 栅栏 |
| Semaphore | 信号量 |
| Exchanger | 交换器 |
| Phaser | 阶段器 |

### 并发集合

| 集合 | 说明 |
|------|------|
| ConcurrentHashMap | 并发 HashMap |
| CopyOnWriteArrayList | 写时复制 List |
| BlockingQueue | 阻塞队列 |
| ConcurrentLinkedQueue | 无锁队列 |

## 代码示例

### 线程池

```java
ExecutorService executor = Executors.newFixedThreadPool(10);

// 提交任务
Future<String> future = executor.submit(() -> {
    return "Hello";
});

// 获取结果
String result = future.get();

// 关闭
executor.shutdown();
```

### CompletableFuture

```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> "Hello")
    .thenApply(s -> s + " World")
    .thenApply(String::toUpperCase);

String result = future.get();  // HELLO WORLD
```

### 并发工具

```java
// CountDownLatch
CountDownLatch latch = new CountDownLatch(3);

for (int i = 0; i < 3; i++) {
    new Thread(() -> {
        // 执行任务
        latch.countDown();
    }).start();
}

latch.await();  // 等待所有任务完成
```

## 最佳实践

1. 优先使用线程池而非直接创建线程
2. 使用 CompletableFuture 处理异步任务
3. 合理设置线程池大小
4. 注意线程安全问题
5. 避免死锁

## 学习资源

- [Java 并发编程实战](https://book.douban.com/subject/10484692/)
- [Java Concurrency in Practice](https://jcip.net/)

