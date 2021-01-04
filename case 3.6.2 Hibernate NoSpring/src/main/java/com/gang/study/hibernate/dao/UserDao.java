package com.gang.study.hibernate.dao;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.hibernate.entity.UserEntity;
import com.gang.study.hibernate.utils.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.UniqueConstraint;
import java.util.Random;

/**
 * @Classname UserDao
 * @Description TODO
 * @Date 2020/6/16 17:34
 * @Created by zengzg
 */

public class UserDao {

    public void create() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        UserEntity emp = new UserEntity();
        emp.setUserId(new Random().nextInt(99));
        emp.setUserName("gang");
        session.save(emp);

        session.getTransaction().commit();
        HibernateUtils.shutdown();
    }

    public void getOne() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        UserEntity userEntity = session.find(UserEntity.class, 1);
        session.getTransaction().commit();
        HibernateUtils.shutdown();

        System.out.println(JSONObject.toJSONString(userEntity));
    }
}
