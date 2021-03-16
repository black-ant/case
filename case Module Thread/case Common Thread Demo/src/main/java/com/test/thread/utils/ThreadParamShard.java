package com.test.thread.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.Serializable;

//@Service
public class ThreadParamShard implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(ThreadParamShard.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is run <-------");


//        new TaskThread(3, "one 111").start();
//        new TaskThread(3, "two 222").start();
//        new TaskThread(3, "three 333").start();


//        new TaskThread1(new BackMsgJSON(3, "one 111")).start();
////        new TaskThread1(new BackMsgJSON(3, "two 222")).start();
////        new TaskThread1(new BackMsgJSON(3, "three 333")).start();


        // --> 没有 sleep ,主线程完成了才会走其他线程
//        BackMsgJSON backMsgJSON = new BackMsgJSON(3, "one 111");
//        logger.info("-----> out 11111 <-------");
//        logger.info("-----> out back :{} <-------", backMsgJSON.getMsg());
//        new TaskThread1(backMsgJSON).start();
//        Thread.sleep(100);
//        logger.info("-----> out 2222 <-------");
//        backMsgJSON.setMsg("two 222");
//        logger.info("-----> out back :{} <-------", backMsgJSON.getMsg());
//        new TaskThread1(backMsgJSON).start();
//        Thread.sleep(100);
//        logger.info("-----> out 3333 <-------");
//        backMsgJSON.setMsg("three 333");
//        logger.info("-----> out back :{} <-------", backMsgJSON.getMsg());
//        new TaskThread1(backMsgJSON).start();


        JSONObject jsonObject = new JSONObject();
        BackMsgJSON backMsgJSON = new BackMsgJSON(3, "one 111");
        logger.info("-----> out 11111 <-------");
        logger.info("-----> out back :{} <-------", backMsgJSON.getMsg());
        new TaskThread2(jsonObject, "11111", backMsgJSON).start();
        Thread.sleep(100);
        logger.info("-----> out 2222 <-------");
        backMsgJSON.setMsg("two 222");
        logger.info("-----> out back :{} <-------", backMsgJSON.getMsg());
        new TaskThread2(jsonObject, "2222", backMsgJSON).start();
        Thread.sleep(100);
        logger.info("-----> out 3333 <-------");
        backMsgJSON.setMsg("three 333");
        logger.info("-----> out back :{} <-------", backMsgJSON.getMsg());
        new TaskThread2(jsonObject, "3333", backMsgJSON).start();
        logger.info("------> this json is :{} <-------", jsonObject.toJSONString());
    }

    static class TaskThread extends Thread {

        private int outNum;
        private int inNum = 5;
        private String msg;

        public TaskThread(int outNum, String msg) {
            this.outNum = outNum;
            this.msg = msg;
        }

        @Override
        public void run() {
            logger.info("------> {}--{}-{} <-------", msg, --outNum, --inNum);
        }
    }


    class TaskThread1 extends Thread {


        private BackMsgJSON backMsgJSON;

        public TaskThread1(BackMsgJSON backMsgJSON) {
            this.backMsgJSON = backMsgJSON;
        }

        @Override
        public void run() {
            backMsgJSON.setInNum(backMsgJSON.getInNum());
            backMsgJSON.setOutNum(backMsgJSON.getOutNum());
            logger.info("------> {}--{}-{} <-------", backMsgJSON.getMsg(), backMsgJSON.getInNum(), backMsgJSON.getOutNum());
        }
    }

    class TaskThread2 extends Thread {

        private BackMsgJSON backMsgJSON;
        private JSONObject jsonObject;
        private String msg;

        public TaskThread2(JSONObject jsonObject, String msg, BackMsgJSON backMsgJSON) {
            this.backMsgJSON = backMsgJSON;
            this.jsonObject = jsonObject;
            this.msg = msg;
        }

        @Override
        public void run() {
            jsonObject.put(backMsgJSON.getMsg(), msg);
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            logger.info("------> {} <-------", jsonObject.toJSONString());
        }
    }


    @Data
    class BackMsgJSON implements Serializable {

        private static final long serialVersionUID = 1L;
        private int outNum;
        private int inNum = 5;
        private String msg;

        public BackMsgJSON(int outNum, String msg) {
            this.outNum = outNum;
            this.msg = msg;
        }

    }
}
