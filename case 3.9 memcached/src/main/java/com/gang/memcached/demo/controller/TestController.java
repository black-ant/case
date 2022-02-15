package com.gang.memcached.demo.controller;

import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2022/2/15
 * @Created by https://www.cnblogs.com/lifan12589/p/14832333.html
 */
@RestController
public class TestController {

    @Autowired
    private MemcachedClient memcachedClient;


    @RequestMapping("/getAndset")
    public void getAndset() {

        //设置缓存   过期时间单位为秒， 0表示永远不过期
        OperationFuture<Boolean> flag = memcachedClient.set("zhongguo", 5, "shanghai");

        Object data;
        data = memcachedClient.get("zhongguo");
        System.out.println("第一次取出数据：" + data);  //shanghai

        try {
            //让  key  过期
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        data = memcachedClient.get("zhongguo");
        System.out.println("第二次取出数据：" + data); //null

    }

    /**
     * Memcached add 命令用于将 value(数据值) 存储在指定的 key(键) 中。
     * <p>
     * 如果 add 的 key 已经存在，则不会更新数据(过期的 key 会更新)，之前的值将仍然保持相同，并且您将获得响应 NOT_STORED。
     */
    @RequestMapping("/add")
    public void add() {

        //设置缓存   过期时间单位为秒， 0表示永远不过期
        OperationFuture<Boolean> flag = memcachedClient.set("zhongguo", 5, "shanghai");

        System.out.println(flag);

        Object data;
        data = memcachedClient.get("zhongguo");
        System.out.println("第一次取出数据：" + data);

        memcachedClient.add("zhongguo", 5, "2"); // 进行 Add操作

        data = memcachedClient.get("zhongguo");
        System.out.println("第二次取出数据：" + data);// 之前的值未过期，新值不生效

        try {
            //让  key  过期
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        memcachedClient.add("zhongguo", 5, "2"); // 再进行 Add操作

        System.out.println("第三次取出数据：" + memcachedClient.get("zhongguo"));  //取出值为2

    }

    /**
     * replace 命令用于替换已存在的 key(键) 的 value(数据值)。
     * <p>
     * 如果 key 不存在，则替换失败，并且您将获得响应 NOT_STORED。
     */
    @RequestMapping("/replace")
    public void replace() {

        //设置缓存   过期时间单位为秒， 0表示永远不过期
        OperationFuture<Boolean> flag = memcachedClient.set("zhongguo", 5, "shanghai");

        System.out.println(flag);

        Object data;
        data = memcachedClient.get("zhongguo");
        System.out.println("第一次取出数据：" + data);

        memcachedClient.replace("zhongguo", 5, "2"); // 进行 replace 操作

        data = memcachedClient.get("zhongguo");
        System.out.println("第二次取出数据：" + data);// 值更新为 2

        try {
            //让  key  过期
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        memcachedClient.replace("zhongguo", 5, "shanghai"); // 再进行 replace 操作

        System.out.println("第三次取出数据：" + memcachedClient.get("zhongguo"));  //key 为空， 取出结果为 null

    }

    /**
     * append 命令用于向已存在 key(键) 的 value(数据值) 后面追加数据
     */
    @RequestMapping("/append")
    public void append() {

        //设置缓存   过期时间单位为秒， 0表示永远不过期
        OperationFuture<Boolean> flag = memcachedClient.set("zhongguo", 5, "shanghai");

        System.out.println(flag);

        Object data;
        data = memcachedClient.get("zhongguo");
        System.out.println("第一次取出数据：" + data);

        memcachedClient.append("zhongguo", "2"); // 进行 append 操作

        data = memcachedClient.get("zhongguo");
        System.out.println("第二次取出数据：" + data);// 值更新为 shanghai2

        try {
            //让  key  过期
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        memcachedClient.append("zhongguo", "shanghai"); // 再进行 append 操作

        System.out.println("第三次取出数据：" + memcachedClient.get("zhongguo"));  //key 为空， 取出结果为 null

    }


    /**
     * prepend  命令用于向已存在 key(键) 的 value(数据值) 后面追加数据
     */
    @RequestMapping("/prepend")
    public void prepend() {

        //设置缓存   过期时间单位为秒， 0表示永远不过期
        OperationFuture<Boolean> flag = memcachedClient.set("zhongguo", 5, "shanghai");

        System.out.println(flag);

        Object data;
        data = memcachedClient.get("zhongguo");
        System.out.println("第一次取出数据：" + data);

        memcachedClient.prepend("zhongguo", "2"); // 进行 prepend 操作

        data = memcachedClient.get("zhongguo");
        System.out.println("第二次取出数据：" + data);// 值更新为 2shanghai

        try {
            //让  key  过期
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        memcachedClient.prepend("zhongguo", "shanghai"); // 再进行 prepend 操作

        System.out.println("第三次取出数据：" + memcachedClient.get("zhongguo"));  //key 为空， 取出结果为 null

    }


    /**
     * CAS（Check-And-Set 或 Compare-And-Swap） 命令用于执行一个"检查并设置"的操作
     * <p>
     * 它仅在当前客户端最后一次取值后，该key 对应的值没有被其他客户端修改的情况下， 才能够将值写入。
     * <p>
     * 检查是通过cas_token参数进行的， 这个参数是Memcach指定给已经存在的元素的一个唯一的64位值。
     */
    @RequestMapping("/cas")
    public void cas() {

        //设置缓存   过期时间单位为秒， 0表示永远不过期
        OperationFuture<Boolean> flag = memcachedClient.set("zhongguo", 5, "shanghai");

        System.out.println(flag);

        Object data;
        data = memcachedClient.get("zhongguo");
        System.out.println("第一次取出数据：" + data);

        //通过 gets 获取 CAS token （令牌）
        CASValue casValue = memcachedClient.gets("zhongguo");

        System.out.println("CAS token --  : " + casValue);  //{CasValue 20/shanghai}

        //使用cas方法来更新数据
        CASResponse casResponse = memcachedClient.cas("zhongguo", casValue.getCas(), 5, "2");

        System.out.println("CAS 响应信息 ：" + casResponse);// OK


        data = memcachedClient.get("zhongguo");
        System.out.println("第二次取出数据：" + data);  // 2


        try {
            //让  key  过期
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //通过 gets 获取 CAS token （令牌）
        CASValue casValue2 = memcachedClient.gets("zhongguo");

        System.out.println("CAS token --  : " + casValue2);  //null

        //使用cas方法来更新数据
        CASResponse casResponse2 = memcachedClient.cas("zhongguo", casValue.getCas(), 5, "2");

        System.out.println("CAS 响应信息 ：" + casResponse2);// NOT_FOUND

        data = memcachedClient.get("zhongguo");
        System.out.println("第三次取出数据：" + data);  // null

    }

    /**
     * delete 命令用于删除已存在的 key(键)。
     */
    @RequestMapping("/delete")
    public void delete() {

        //设置缓存   过期时间单位为秒， 0表示永远不过期
        OperationFuture<Boolean> flag = memcachedClient.set("zhongguo", 5, "shanghai");

        System.out.println(flag);

        Object data;
        data = memcachedClient.get("zhongguo");
        System.out.println("第一次取出数据：" + data); //shanghai

        OperationFuture<Boolean> zhongguo = memcachedClient.delete("zhongguo");

        System.out.println(zhongguo.getStatus()); //{OperationStatus success=true:  DELETED}

        data = memcachedClient.get("zhongguo");
        System.out.println("第二次取出数据：" + data); //null

        //没有 key 的情况下
        OperationFuture<Boolean> zhongguo2 = memcachedClient.delete("zhongguo");

        System.out.println(zhongguo2.getStatus()); //{OperationStatus success=false:  NOT_FOUND}

    }

    /**
     * Memcached incr 与 decr 命令用于对已存在的 key(键) 的数字值进行自增或自减操作。
     * <p>
     * incr 与 decr 命令操作的数据必须是十进制的32位无符号整数。
     * <p>
     * 如果 key 不存在返回 NOT_FOUND，如果键的值不为数字，则返回 CLIENT_ERROR，其他错误返回 ERROR。
     */
    @RequestMapping("/incrOrDecr")
    public void incrOrDecr() {

        //设置缓存   过期时间单位为秒， 0表示永远不过期
        OperationFuture<Boolean> flag = memcachedClient.set("zhongguo", 5, "1000");

        System.out.println(flag);

        Object data;
        data = memcachedClient.get("zhongguo");
        System.out.println("第一次取出数据：" + data); // 1000

        long incr = memcachedClient.incr("zhongguo", 100);

        System.out.println("取出 incr 之后的数据：" + memcachedClient.get("zhongguo")); // 1100

        long decr = memcachedClient.decr("zhongguo", 100);

        System.out.println("取出 decr 之后的数据：" + memcachedClient.get("zhongguo")); // 1000


        memcachedClient.append("zhongguo", "shanghai");

        System.out.println("取出 append 之后的数据：" + memcachedClient.get("zhongguo")); //  1000shanghai


        //ERROR net.spy.memcached.protocol.ascii.MutatorOperationImpl:  Error:  CLIENT_ERROR cannot increment or decrement non-numeric value
        long incr2 = memcachedClient.incr("zhongguo", 100);
        System.out.println("取出 incr 之后的数据：" + memcachedClient.get("zhongguo")); //  1000shanghai


        //ERROR net.spy.memcached.protocol.ascii.MutatorOperationImpl:  Error:  CLIENT_ERROR cannot increment or decrement non-numeric value
        long decr2 = memcachedClient.decr("zhongguo", 100);
        System.out.println("取出 decr 之后的数据：" + memcachedClient.get("zhongguo"));//  1000shanghai


        try {
            //让  key  过期
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //key 过期后 未报错  但返回 的都是 -1

        long incr3 = memcachedClient.incr("zhongguo", 100);

        System.out.println(incr3); //  -1
        System.out.println("取出 incr 之后的数据：" + memcachedClient.get("zhongguo")); //  null


        long decr3 = memcachedClient.decr("zhongguo", 100);

        System.out.println(decr3); //  -1
        System.out.println("取出 decr 之后的数据：" + memcachedClient.get("zhongguo"));//  null

    }
}
