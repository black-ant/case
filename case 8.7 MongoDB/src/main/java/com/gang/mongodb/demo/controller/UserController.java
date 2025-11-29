package com.gang.mongodb.demo.controller;

import com.gang.mongodb.demo.entity.Person;
import com.gang.mongodb.demo.entity.User;
import com.gang.mongodb.demo.repository.UserRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2021/9/21
 * @Created by zengzg
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final List<String> TYPE = Arrays.asList("湖北", "湖南", "福建", "四川");

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(5000), new ThreadPoolExecutor.CallerRunsPolicy());

    private static AtomicInteger num = new AtomicInteger(0);

    @Resource
    private UserRepositoryImpl repository;


    @GetMapping("save")
    public String save() {
        logger.info("------> this is in createUser <-------");
        Long id = new Date().getTime() + new Random().nextInt(999999);
        repository.saveUser(buildUser(id));
        return "sccuess";
    }

    @GetMapping("saveList")
    public String saveList(@RequestParam("num") Integer num) {
        logger.info("------> this is in saveList <-------");
        num = num == null ? 30 : num;
        for (int i = 0; i < num; i++) {
            Long id = new Date().getTime() + new Random().nextInt(999999);
            threadPoolExecutor.submit(() -> {
                logger.info("------> 创建 :{} <-------", id);
                repository.saveUser(buildUser(id));
            });
        }
        return "sccuess";
    }

    private User buildUser(Long id) {
        User user = new User();
        user.setAddress("address");
        user.setNickName("nickName:" + new Random().nextInt(999));
        setDefaultValue(user, null, "");
        user.setSelfId(id);
        user.setCreateTime(new Date());
        user.setStatus(new Random().nextInt(5));
        user.setSex(new Random().nextInt(3));
        return user;
    }

    private Person buildPerson(Long id) {
        Person person = new Person();
        setDefaultValue(person, null, "");
        person.setId(id);
        return person;
    }

    public static void setDefaultValue(Object sourceObject, String targetString, String defaultValue) {
        if (sourceObject != null) {
            try {
                Class<?> clazz = sourceObject.getClass();
                Field[] fields = clazz.getDeclaredFields();
                Object objectCopy = clazz.getConstructor(new Class[]{}).newInstance();
                org.springframework.beans.BeanUtils.copyProperties(sourceObject, objectCopy);
                for (Field field : fields) {
                    //修饰符
                    int mod = field.getModifiers();
                    if (Modifier.isStatic(mod)) {
                        continue;
                    }
                    //属性名称
                    String fieldName = field.getName();
                    if (fieldName.equals("serialVersionUID")) {
                        continue;
                    }
                    // 获得属性的首字母并转换为大写，与setXXX对应
                    String firstLetter = fieldName.substring(0, 1).toUpperCase();
                    String setMethodName = "set" + firstLetter + fieldName.substring(1);
                    //设置属性的可访问标识
                    boolean accessible = field.isAccessible();
                    //  log.info("可访问标识："+accessible);
                    field.setAccessible(true);
                    Method setMethod = clazz.getMethod(setMethodName, field.getType());
                    Object fieldValueO = field.get(sourceObject);
                    // 获取字段类型
                    String classType = field.getType().toString();
                    if (targetString != null) {
                        if (fieldValueO instanceof String) {
                            String fieldValue = (String) field.get(sourceObject);
                            if (targetString.equals(fieldValue)) {
                                setMethod.invoke(objectCopy, defaultValue);//调用对象的setXXX方法
                            }
                        } else if (fieldValueO instanceof Integer) {
                            String fieldValue = (String) field.get(sourceObject);
                            if (targetString.equals(fieldValue)) {
                                setMethod.invoke(objectCopy, 0);//调用对象的setXXX方法
                            }
                        }
                    } else {
                        Object o = field.get(sourceObject);
                        if (o == null) {
                            //根据属性类型的不同，分别赋值，字符串类型赋默认值空串，integer类型赋值为0 (经过改版后，我的写法)
                            if ("class java.lang.String".equals(classType)) {
                                defaultValue = getPrefix() + new Random().nextInt(99);
                                setMethod.invoke(objectCopy, defaultValue);
                            } else if ("class java.lang.Integer".equals(classType)) {
                                setMethod.invoke(objectCopy, new Random().nextInt(99));//调用对象的setXXX方法
                            } else if ("class java.lang.Long".equals(classType)) {
                                setMethod.invoke(objectCopy, Long.valueOf(new Random().nextInt(99)));//调用对象的setXXX方法
                            }

                        }
                    }
                    field.setAccessible(accessible);
                }
                org.springframework.beans.BeanUtils.copyProperties(objectCopy, sourceObject);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getPrefix() {
        return TYPE.get(new Random().nextInt(TYPE.size()));
    }

    @GetMapping("list")
    public List<User> list() {
        logger.info("------> this is in createUser <-------");
        return repository.findAll();
    }

}
