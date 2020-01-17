package com.gang.study.reflect.javareflect.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.reflect.javareflect.entity.InnerClassEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

@Service
public class FliedReflectService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() {
        try {
            String classPath = "com.gang.study.reflect.javareflect.entity.InnerClassEntity";
            Class clazz = Class.forName(classPath);
            InnerClassEntity innerClassEntity = (InnerClassEntity) clazz.newInstance();
            innerClassEntity.setUserType("User");
            innerClassEntity.setInner(new InnerClassEntity.Inner("ant-black", 18));
            function(innerClassEntity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public <T> void function(T t) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            logger.info("------> this field is :{} <-------", JSONObject.toJSONString(field));
            logger.info("------> this field name is :{} <-------", JSONObject.toJSONString(field.getName()));
            Class clazz = field.getType();
        }
        // --> 内部类 使用 $
        Class innerClass = Class.forName("com.gang.study.reflect.javareflect.entity.InnerClassEntity$Inner");
//        Object obj = t.getClass().getDeclaredConstructor(new Class[]{innerClass});
//        logger.info("------> this declaredConstructor is :{} <-------", obj);

        // --> 获取内部类
        Class innerClazz[] = t.getClass().getDeclaredClasses();
        for (Class claszInner : innerClazz) {
            Field[] field3 = claszInner.getDeclaredFields();
            for (Field field : field3) {
                try {
                    field.setAccessible(Boolean.TRUE);
                    logger.info("----> inner filed :{} <-------", field.getName());
                    logger.info("----> inner value :{} <-------", JSONObject.toJSONString(field.get(claszInner)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        // 反射构造器
        Constructor<?>[] conList = t.getClass().getDeclaredConstructors();
        for (Constructor obj : conList) {
//            logger.info("------> this cons is :{} <-------", JSONObject.toJSONString(obj));
            Field[] fields1 = obj.getClass().getDeclaredFields();
            for (Field field : fields1) {
                field.setAccessible(Boolean.TRUE);
//                logger.info("------> this field name is :{} value :{} <-------", JSONObject.toJSONString(field.getName()), field.get(obj));
            }
        }


    }

    /**
     * 　　>  getName()：返回String形式的该类的名称。
     * 　　>  newInstance()：根据某个Class对象产生其对应类的实例，它调用的是此类的默认构造方法(没有默认无参构造器会报错)
     * 　　>  getClassLoader()：返回该Class对象对应的类的类加载器。
     * 　　>  getSuperClass()：返回某子类所对应的直接父类所对应的Class对象
     * 　　>  isArray()：判定此Class对象所对应的是否是一个数组对象
     * 　　>  getComponentType() ：如果当前类表示一个数组，则返回表示该数组组件的 Class 对象，否则返回 null。
     * 　　>  getConstructor(Class[]) :返回当前 Class 对象表示的类的指定的公有构造子对象。
     * 　　>  getConstructors() :返回当前 Class 对象表示的类的所有公有构造子对象数组。
     * 　　>  getDeclaredConstructor(Class[]) :返回当前 Class 对象表示的类的指定已说明的一个构造子对象。
     * 　　>  getDeclaredConstructors() :返回当前 Class 对象表示的类的所有已说明的构造子对象数组。
     * 　　>  getDeclaredField(String) :返回当前 Class 对象表示的类或接口的指定已说明的一个域对象。
     * 　　>  getDeclaredFields() :返回当前 Class 对象表示的类或接口的所有已说明的域对象数组。
     * 　　>  getDeclaredMethod(String, Class[]) :返回当前 Class 对象表示的类或接口的指定已说明的一个方法对象。
     * 　　>  getDeclaredMethods() :返回 Class 对象表示的类或接口的所有已说明的方法数组。
     * 　　>  getField(String) :返回当前 Class 对象表示的类或接口的指定的公有成员域对象。
     * 　　>  getFields() :返回当前 Class 对象表示的类或接口的所有可访问的公有域对象数组。
     * 　　>  getInterfaces() :返回当前对象表示的类或接口实现的接口。
     * 　　>  getMethod(String, Class[]) :返回当前 Class 对象表示的类或接口的指定的公有成员方法对象。
     * 　　>  getMethods() :返回当前 Class 对象表示的类或接口的所有公有成员方法对象数组，包括已声明的和从父类继承的方法。
     * 　　>  isInstance(Object) :此方法是 Java 语言 instanceof 操作的动态等价方法。
     * 　　>  isInterface() :判定指定的 Class 对象是否表示一个接口类型
     * 　　>  isPrimitive() :判定指定的 Class 对象是否表示一个 Java 的基类型。
     * 　　>  newInstance() ：创建类的新实例
     */
}
