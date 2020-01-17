

# ThreadLocal 理解

## 前言

```
> ThreadLocal虽然提供了一种解决多线程环境下成员变量的问题，但是它并不是解决多线程共享变量的问题


> 每个线程都有自己的局部变量，独立于变量的初始化副本，Thread Local 将状态与某一个线程相关联

> 区别于线程同步机制 ，线程同步机制是通过多个线程共享一个变量 ， 而 ThreadLocal 是每一个线程创建一个单独的变量副本
```



## Thread Local 知识点

#### Thead Local 中定义的方法

```
get () : 返回该线程局部变量的当前线程副本中的值
initialValue() : 返回次线程局部变量的初始值
remove() : 移除该线程变量当前线程的值
set

主要部分 ： 
ThreadLocalMap
上述方法都是基于该类实现的操作。key 为当前TheadLocal 对象 ，value 为当前线程变量的副本

> ThreadLocal实例本身是不存储值，它只是提供了一个在当前线程中找到副本值得key
> 是ThreadLocal包含在Thread中，而不是Thread包含在ThreadLocal中

```

#### threadLocal 的使用方式

```java

private static ThreadLocal<Integer> seqCount = new ThreadLocal<Integer>(){
        // 实现initialValue()
        public Integer initialValue() {
            return 0;
        }
};

 SeqCount seqCount = new SeqCount();
 seqCount.set(seqCount.get() + 1);


```



#### ThreadLocal 源码解析

##### Thread Local Map

```java
static class Entry extends WeakReference<ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
}

// set ()===========================
set()操作除了存储元素外，还有一个很重要的作用，就是replaceStaleEntry()和cleanSomeSlots()，这两个方法可以清除掉key == null 的实例，防止内存泄

> 在set()方法中还有一个变量很重要：threadLocalHashCode
> nextHashCode表示分配下一个ThreadLocal实例的threadLocalHashCode的值
> HASH_INCREMENT则表示分配两个ThradLocal实例的threadLocalHashCode的增量

// getEntry() =======================
当key == null时，调用了expungeStaleEntry()方法，该方法用于处理key == null，有利于GC回收

expungeStaleEntry(i);

// //== set 对 map 基本操作==================
public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
//  get 同理 =======================
 ThreadLocalMap.Entry e = map.getEntry(this);

// ====================
initialValue（） -> 子类要求实现
```





#### ThreadLocal 内存泄漏

```
每个Thread都有一个ThreadLocal.ThreadLocalMap的map，该map的key为ThreadLocal实例，它为一个弱引用，我们知道弱引用有利于GC回收

当ThreadLocal的key == null时，GC就会回收这部分空间，但是value却不一定能够被回收，因为他还与Current Thread存在一个强引用关系

所以说只要这个线程对象能够及时被GC回收，就不会出现内存泄漏

在前面提过，在ThreadLocalMap中的setEntry()、getEntry()，如果遇到key == null的情况，会对value设置为null。当然我们也可以显示调用ThreadLocal的remove()方法进行处理

```



#### 总结

```
ThreadLocal 不是用于解决共享变量的问题的，也不是为了协调线程同步而存在，而是为了方便每个线程处理自己的状态而引入的一个机制。这点至关重要。

每个Thread内部都有一个ThreadLocal.ThreadLocalMap类型的成员变量，该成员变量用来存储实际的ThreadLocal变量副本。

ThreadLocal并不是为线程保存对象的副本，它仅仅只起到一个索引的作用。它的主要木得视为每一个线程隔离一个类的实例，这个实例的作用范围仅限于线程内部。
```

