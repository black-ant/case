package com.gang.assist.demo.service;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Modifier;

/**
 * @Classname TemplateService
 * @Description TODO
 * @Date 2021/10/13
 * @Created by zengzg
 */
@Component
public class TemplateService implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void test() throws Exception {
        //CtClass对象容器
        ClassPool classPool = ClassPool.getDefault();
        //CtClass对象容器中创建一个public的JATest类
        CtClass jATestClazz = classPool.makeClass("com.an.bytecode.javaassist.JATest");

        //***属性操作
        //JATest类中添加private int id
        CtField ctIdField = new CtField(classPool.getCtClass("int"), "id", jATestClazz);
        ctIdField.setModifiers(Modifier.PRIVATE);
        jATestClazz.addField(ctIdField);

        //JATest类中添加private String username
        CtField ctUserNameField = new CtField(classPool.getCtClass("java.lang.String"), "username", jATestClazz);
        ctUserNameField.setModifiers(Modifier.PRIVATE);
        jATestClazz.addField(ctUserNameField);

        //添加getter,setter方法
        jATestClazz.addMethod(CtNewMethod.getter("getId", ctIdField));
        jATestClazz.addMethod(CtNewMethod.getter("setId", ctIdField));
        jATestClazz.addMethod(CtNewMethod.getter("getUsername", ctUserNameField));
        jATestClazz.addMethod(CtNewMethod.getter("setUsername", ctUserNameField));

        //添加构造函数
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, jATestClazz);
        //添加构造函数方法体
        StringBuffer sb = new StringBuffer();
        sb.append("{\n").append("this.id = 27;\n").append("this.username=\"carl\";\n}");
        ctConstructor.setBody(sb.toString());
        jATestClazz.addConstructor(ctConstructor);

        // 添加自定义方法
        CtMethod method = new CtMethod(CtClass.voidType, "say", new CtClass[]{}, jATestClazz);
        method.setModifiers(Modifier.PUBLIC);
        StringBuffer printSb = new StringBuffer();
        printSb.append("{\nSystem.out.println(\"begin!\");\n")
                .append("System.out.println(id);\n")
                .append("System.out.println(username);\n")
                .append("System.out.println(\"end!\");\n")
                .append("}");
        method.setBody(printSb.toString());
        jATestClazz.addMethod(method);

        //生成一个Class对象
        Class<?> clazz = jATestClazz.toClass();
        Object object = clazz.newInstance();

        //反射执行方法
        clazz.getMethod("say", new Class[]{}).invoke(object, new Object[]{});

        //将生成的class写入文件中
        FileOutputStream fileOutputStream = new FileOutputStream(new File("JATest.class"));
        fileOutputStream.write(jATestClazz.toBytecode());
        fileOutputStream.close();

    }
}
