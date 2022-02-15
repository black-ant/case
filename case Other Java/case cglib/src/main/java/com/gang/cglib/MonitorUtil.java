package com.gang.cglib;

/**
 * @Classname MonitorUtil
 * @Description TODO
 * @Date 2021/10/6
 * @Created by zengzg
 */
public class MonitorUtil {

    private static ThreadLocal<Long> tl = new ThreadLocal<Long>();

    public static void start() {
        tl.set(System.currentTimeMillis());
    }

    /**
     * 结束时打印耗时
     *
     * @param methodName 方法名
     */
    public static void finish(String methodName) {
        long finishTime = System.currentTimeMillis();
        System.out.println(methodName + "方法执行耗时" + (finishTime - tl.get()) + "ms");
    }
}
