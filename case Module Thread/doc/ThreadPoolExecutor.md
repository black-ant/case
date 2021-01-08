# J.U.C之线程池：ThreadPoolExecutor



```
J.U.C之线程池：ThreadPoolExecutor

线程的五种状态 ：
新建 、 就绪 、运行 、阻塞 、 死亡

AtomicInteger 变量 
	: 记录了“线程池中的任务数量”和“线程池的状态”两个信息
	: 共32位，其中高3位表示”线程池状态”，低29位表示”线程池中的任务数量”
	
RUNNING：处于RUNNING状态的线程池能够接受新任务，以及对新添加的任务进行处理。

SHUTDOWN：处于SHUTDOWN状态的线程池不可以接受新任务，但是可以对已添加的任务进行处理。

STOP：处于STOP状态的线程池不接收新任务，不处理已添加的任务，并且会中断正在处理的任务。

TIDYING：当所有的任务已终止，ctl记录的”任务数量”为0，线程池会变为TIDYING状态。当线程池变为TIDYING状态时，会执行钩子函数terminated()。terminated()在ThreadPoolExecutor类中是空的，若用户想在线程池变为TIDYING时，进行相应的处理；可以通过重载terminated()函数来实现。

TERMINATED：线程池彻底终止的状态。	


RUNNING            -- 对应的高3位值是111。
SHUTDOWN       -- 对应的高3位值是000。
STOP                   -- 对应的高3位值是001。
TIDYING              -- 对应的高3位值是010。
TERMINATED     -- 对应的高3位值是011。
```

#### 创建线程池

```java
通过构造器创建

    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler)
    
> corePoolSize : 线程池中核心线程的数量
> maximumPoolSize : 线程次中最大线程数
> keepAliveTime : 线程空闲的时间
> unit ： keepAliveTime的单位
> workQueue : 用来保存等待执行的任务的阻塞队列，等待的任务必须实现Runnable接口
	: ArrayBlockingQueue :基于数组结构的有界阻塞队列，FIFO
	: LinkedBlockingQueue 基于链表结构的有界阻塞队列，FIFO
	: SynchronousQueue : 不存储元素的阻塞队列，每个插入操作都必须等待一个移出操作
	: PriorityBlockingQueue : 具有优先界别的阻塞队列
	
> threadFactory : 用于设置创建线程的工厂 ， 通过 newThead 方法提供创建线程的功能。
	: 创建的线程都是“非守护线程”而且“线程优先级都是Thread.NORM_PRIORITY

> handler : 线程池的拒绝策略
	: 指将任务添加到线程池中时，线程池拒绝该任务所采取的相应策略
	AbortPolicy：直接抛出异常，默认策略；
	CallerRunsPolicy：用调用者所在的线程来执行任务；
	DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
	DiscardPolicy：直接丢弃任务；

	
```

### Executor 线程池

```
FixedThreadPool : 可重用固定线程数的线程池
SingleThreadExecutor : 使用单个worker线程的Executor
CachedThreadPool : 会根据需要创建新线程的线程池
```

http://cmsblogs.com/?p=2448