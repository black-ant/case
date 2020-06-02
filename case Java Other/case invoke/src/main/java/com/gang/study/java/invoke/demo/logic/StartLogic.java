package com.gang.study.java.invoke.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname StartLogic
 * @Description TODO
 * @Date 2020/6/2 15:29
 * @Created by zengzg
 */
@Component
public class StartLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("this is in logic");
        testJDKProxy();
    }


    public void testJDKProxy() {
        //创建测试对象；
        ExcuterServiceImpl nativeTest = new ExcuterServiceImpl();
        ExcuterServiceImpl decorator = new ExcuterServiceImpl();
        ExcuterServiceImpl jdkProxy = JDKProxyService.newProxyInstance(nativeTest);
        ExcuterServiceImpl cglibProxy = CglibProxyService.newProxyInstance(ExcuterServiceImpl.class);

        //预热一下；
        int preRunCount = 10000;
        runWithoutMonitor(nativeTest, preRunCount);
        runWithoutMonitor(decorator, preRunCount);
        runWithoutMonitor(cglibProxy, preRunCount);
        runWithoutMonitor(jdkProxy, preRunCount);

        //执行测试；
        Map<String, ExcuterServiceImpl> tests = new LinkedHashMap<String, ExcuterServiceImpl>();
        tests.put("Native   ", nativeTest);
        tests.put("Decorator", decorator);
        tests.put("Dynamic  ", jdkProxy);
        tests.put("Cglib    ", cglibProxy);
        int repeatCount = 3;
        int runCount = 1000000;
        runTest(repeatCount, runCount, tests);
        runCount = 50000000;
        runTest(repeatCount, runCount, tests);
    }

    private static void runTest(int repeatCount, int runCount, Map<String, ExcuterServiceImpl> tests) {
        System.out.println(String.format("\n==================== run test : [repeatCount=%s] [runCount=%s] [java" +
                ".version=%s] ====================", repeatCount, runCount, System.getProperty("java.version")));
        for (int i = 0; i < repeatCount; i++) {
            System.out.println(String.format("\n--------- test : [%s] ---------", (i + 1)));
            for (String key : tests.keySet()) {
                runWithMonitor(tests.get(key), runCount, key);
            }
        }
    }

    private static void runWithoutMonitor(ExcuterServiceImpl test, int runCount) {
        for (int i = 0; i < runCount; i++) {
            test.doExcuter(i);
        }
    }

    private static void runWithMonitor(ExcuterServiceImpl test, int runCount, String tag) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < runCount; i++) {
            test.doExcuter(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("[" + tag + "] Elapsed Time:" + (end - start) + "ms");
    }

    public void doExampleOne() {
        try {
            Class clazz = Class.forName("com.gang.study.java.invoke.demo.logic.ExcuterServiceImpl");
            this.getClass().getClassLoader().loadClass("com.gang.study.java.invoke.demo.logic.ExcuterServiceImpl");
            Method method = clazz.getMethod("doExcuter");
            method.invoke(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有接口的实现类
     *
     * @return
     */
    public static List<Class> getAllInterfaceAchieveClass(Class clazz) {
        ArrayList<Class> list = new ArrayList<>();
        //判断是否是接口
        if (clazz.isInterface()) {
            try {
                ArrayList<Class> allClass = getAllClassByPath(clazz.getPackage().getName());
                /**
                 * 循环判断路径下的所有类是否实现了指定的接口
                 * 并且排除接口类自己
                 */
                for (int i = 0; i < allClass.size(); i++) {

                    //排除抽象类
                    if (Modifier.isAbstract(allClass.get(i).getModifiers())) {
                        continue;
                    }
                    //判断是不是同一个接口
                    if (clazz.isAssignableFrom(allClass.get(i))) {
                        if (!clazz.equals(allClass.get(i))) {
                            list.add(allClass.get(i));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("出现异常");
            }
        }
        return list;
    }

    /**
     * 从指定路径下获取所有类
     *
     * @return
     */
    public static ArrayList<Class> getAllClassByPath(String packagename) {
        ArrayList<Class> list = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packagename.replace('.', '/');
        try {
            ArrayList<File> fileList = new ArrayList<>();
            Enumeration<URL> enumeration = classLoader.getResources(path);
            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                fileList.add(new File(url.getFile()));
            }
            for (int i = 0; i < fileList.size(); i++) {
                list.addAll(findClass(fileList.get(i), packagename));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 如果file是文件夹，则递归调用findClass方法，或者文件夹下的类
     * 如果file本身是类文件，则加入list中进行保存，并返回
     *
     * @param file
     * @param packagename
     * @return
     */
    private static ArrayList<Class> findClass(File file, String packagename) {
        ArrayList<Class> list = new ArrayList<>();
        if (!file.exists()) {
            return list;
        }
        File[] files = file.listFiles();
        for (File file2 : files) {
            if (file2.isDirectory()) {
                assert !file2.getName().contains(".");//添加断言用于判断
                ArrayList<Class> arrayList = findClass(file2, packagename + "." + file2.getName());
                list.addAll(arrayList);
            } else if (file2.getName().endsWith(".class")) {
                try {
                    //保存的类文件不需要后缀.class
                    list.add(Class.forName(packagename + '.' + file2.getName().substring(0,
                            file2.getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


}
