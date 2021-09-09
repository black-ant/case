package com.gang.cloud.template.demo.controller;

import com.gang.cloud.template.demo.client.ProductFeignClient;
import com.gang.cloud.template.demo.entity.NoDataOrder;
import com.gang.cloud.template.demo.service.BuyProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @Classname OrderController
 * @Description TODO
 * @Created by zengzg
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BuyProductService productService;

    @Autowired
    private ProductFeignClient productFeignClient;

    @GetMapping("list")
    public Collection<NoDataOrder> list() {
        List<NoDataOrder> list = new LinkedList<>();
        list.add(new NoDataOrder());
        return list;
    }

    @GetMapping("get/{id}")
    public NoDataOrder list(@PathVariable("id") String id) {
        return new NoDataOrder();
    }


    @GetMapping("buy")
    public String buy() {
        return productService.buySingleProduct("1", "1");
    }


    @GetMapping("test")
    @HystrixCommand(
            fallbackMethod = "hystrix_fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
            })
    public String test() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @GetMapping("test001")
    public String test1() {
        logger.info("------> 调用 Server 端服务 <-------");
        return productFeignClient.test();
    }

    @GetMapping("test002")
    public String test2() {
        logger.info("------> 调用 Server 端服务 <-------");
        return productFeignClient.test002();
    }

    /**
     * execution.isolation.strategy
     * 指示使用哪种隔离策略，有两种：thread（线程池，默认）并发请求受到线程池最大线程限制，semaphore（信号量）受到信号量计数的限制，一般使用线程的方式
     * <p>
     * execution.isolation.thread.timeoutInMilliseconds
     * 用于设置超时的时间，单位是毫秒，默认为1s
     * <p>
     * execution.timeout.enabled
     * 用于指示是否启用超时，默认为true
     * <p>
     * execution.isolation.thread.interruptOnTimeout
     * 是否在服务超时后中断，默认为true，需要注意在 JVM 中我们无法强制中断一个线程，如果 Hystrix 方法里没有处理中断信号的逻辑，那么中断会被忽略。
     * <p>
     * execution.isolation.thread.interruptOnCancel
     * 是否在服务取消后中断，默认为false
     * <p>
     * execution.isolation.semaphore.maxConcurrentRequests
     * 设置信号量最大请求数，需要设置execution.isolation.strategy属性为semaphore，默认为10
     *
     * @return
     */

    public String hystrix_fallback() {
        return "hystrix run";
    }

}
