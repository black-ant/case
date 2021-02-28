package com.test.thread.to;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname CommonTO
 * @Description TODO
 * @Date 2021/2/28 19:42
 * @Created by zengzg
 */
public class CommonTO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Integer check;
    private Integer showNum = 0;

    private String lock = new String("lock");
    private String lock2 = "lock";

    public void operation(Integer check) {

        //  案例一 : 校验无锁情况
//        functionShow(check);

        // 案例二 : 校验 synchronized 方法
        functionShowSynchronized(check);

        // 案例三 : 校验 synchronized 代码块
//        statementShowSynchronized(check);

        // 案例四 : 校验 代码块以 Class 为对象
//        classShowSynchronized(check);

        // 案例五 : 同步代码块 Object
//        objectShowSynchronized(check);

        // 案例五 : 同步代码块 Object
//        objectStringShowSynchronized(check);
    }

    /**
     *
     */
    public void functionShow(Integer check) {
        logger.info("------> check is {} <-------", check);
        if (check == 0) {
            showNum = 100;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (check == 1) {
            showNum = 200;
        }
        logger.info("------> check is Over {} :{}", check, showNum);
    }

    /**
     * 同步方法
     */
    synchronized public void functionShowSynchronized(Integer check) {
        logger.info("------> check is {} <-------", check);
        if (check == 0) {
            showNum = 100;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (check == 1) {
            showNum = 200;
        }
        logger.info("------> check is Over synchronized {} :{}", check, showNum);
    }

    /**
     *
     */
    public void statementShowSynchronized(Integer check) {
        logger.info("------> check is {} <-------", check);
        synchronized (this) {
            if (check == 0) {
                showNum = 100;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (check == 1) {
                showNum = 200;
            }
        }
        logger.info("------> check is Over synchronized {} :{}", check, showNum);
    }

    public void classShowSynchronized(Integer check) {
        logger.info("check is {} <-------", check);
        synchronized (CommonTO.class) {
            if (check == 0) {
                showNum = 100;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (check == 1) {
                showNum = 200;
            }
        }
        logger.info("check is Over synchronized {} :{}", check, showNum);
    }

    public void objectShowSynchronized(Integer check) {
        logger.info("check is {} <-------", check);
        synchronized (lock) {
            if (check == 0) {
                showNum = 100;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (check == 1) {
                showNum = 200;
            }
        }
        logger.info("check is Over synchronized {} :{}", check, showNum);
    }

    public void objectStringShowSynchronized(Integer check) {
        logger.info("check is {} <-------", check);
        synchronized (lock2) {
            if (check == 0) {
                showNum = 100;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (check == 1) {
                showNum = 200;
            }
        }
        logger.info("check is Over synchronized {} :{}", check, showNum);
    }


    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }

    public Integer getShowNum() {
        return showNum;
    }

    public void setShowNum(Integer showNum) {
        this.showNum = showNum;
    }
}
