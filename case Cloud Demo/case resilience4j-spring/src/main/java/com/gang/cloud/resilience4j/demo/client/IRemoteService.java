package com.gang.cloud.resilience4j.demo.client;

import java.util.concurrent.TimeoutException;

/**
 * @Classname IRemoteService
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
public interface IRemoteService {

    String process() throws TimeoutException, InterruptedException;

}
