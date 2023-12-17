package com.gang.study.io.demo.streamobject;

import com.alibaba.fastjson.JSONObject;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Classname ObjectStreamService
 * @Description TODO
 * @Date 2022/8/23
 * @Created by zengzg
 */
@Service
public class ObjectStreamService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ObjectStreamTO objectStreamTO = new ObjectStreamTO();
        objectStreamTO.setAge(10);
        objectStreamTO.setUsername("Test");

        ObjectStreamTO.InnerTO innerTO = new ObjectStreamTO.InnerTO();
        innerTO.setChannel("Boss");
        innerTO.setUserNo("10096");
        objectStreamTO.setInnerTO(innerTO);

        byte[] object = serialize(objectStreamTO);

        Object objectStreamTODecode = deSerialize(object);

        logger.info("------> objectStreamTODecode :{} <-------", JSONObject.toJSONString(objectStreamTODecode));

    }


    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream output = null;

        try {
            baos = new ByteArrayOutputStream(1024);
            output = new ObjectOutputStream(baos);
            output.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                    baos.close();
                } catch (IOException var10) {
                }
            }

        }

        return baos != null ? baos.toByteArray() : null;
    }

    public static Object deSerialize(byte[] in) {
        Object obj = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream input = null;

        try {
            bais = new ByteArrayInputStream(in);
            input = new ObjectInputStream(bais);
            obj = input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                    bais.close();
                } catch (IOException var11) {
                }
            }

        }

        return obj;
    }
}
