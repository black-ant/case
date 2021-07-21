package com.gang.study.thread.future.gangthreadfuture;

import com.gang.study.thread.future.gangthreadfuture.service.StartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GangThreadFutureApplication {

    @Autowired
    private StartService startService;

    public static void main(String[] args) {
        SpringApplication.run(GangThreadFutureApplication.class, args);
    }

}
