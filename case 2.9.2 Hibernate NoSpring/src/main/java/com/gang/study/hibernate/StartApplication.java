package com.gang.study.hibernate;

import com.gang.study.hibernate.dao.UserDao;
import org.hibernate.Session;

/**
 * @Classname StartApplication
 * @Description TODO
 * @Date 2020/6/16 17:32
 * @Created by zengzg
 */

public class StartApplication {

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        //        userDao.create();
        userDao.getOne();
    }
}
