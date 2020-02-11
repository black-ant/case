package com.gang.study.springthread.demo.logic;

import com.gang.study.springthread.demo.to.TestTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/2/11 11:32
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    @Autowired
    private TestService testService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        for (int i = 0; i < 20; i++) {
        //            Thread t = new Thread() {
        //                @Override
        //                public void run() {
        //                    testService.run();
        //                }
        //            };
        //            t.start();
        //        }

        //        TestTO testTO = new TestTO("1");
        //        for (int i = 0; i < 20; i++) {
        //            Thread t = new Thread() {
        //                @Override
        //                public void run() {
        //
        //                }
        //            };
        //            t.start();
        //        }

        //        TestTO testTO = new TestTO("1");
        //        for (int i = 0; i < 20; i++) {
        //            Thread t = new Thread() {
        //                @Override
        //                public void run() {
        //                    testService.run01(testTO);
        //                }
        //            };
        //            t.start();
        //        }

        TestTO testTO = new TestTO("1");
        RunThread runThread = new RunThread(testService, testTO);
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(runThread);
            thread.start();
        }
    }

}

class RunThread extends Thread {

    private TestService testService;
    private TestTO testTO;

    public RunThread(TestService testService, TestTO testTO) {
        this.testService = testService;
        this.testTO = testTO;
    }

    @Override
    public void run() {
        testTO.setThreadValue(1);
        testService.run(testTO);
    }
}

