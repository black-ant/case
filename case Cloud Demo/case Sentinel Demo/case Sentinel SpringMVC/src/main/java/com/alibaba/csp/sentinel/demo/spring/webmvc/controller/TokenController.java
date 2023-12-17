package com.alibaba.csp.sentinel.demo.spring.webmvc.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/token")
public class TokenController {

    @GetMapping("/test")
    public void test(){
        TokenBucket bucket = new TokenBucket(10, 2);  // 令牌桶容量为 10，每秒生成 2 个令牌

        // 模拟一系列请求
        for (int i = 1; i <= 15; i++) {
            if (bucket.request(1)) {
                System.out.println("Request " + i + ": Token granted");
            } else {
                System.out.println("Request " + i + ": Token denied");
            }
        }
    }

    public class TokenBucket {
        private int capacity;           // 令牌桶容量，表示桶可以存储的最大令牌数
        private int rate;               // 令牌生成速率，表示每秒生成的令牌数
        private long lastRefillTime;    // 上次令牌生成的时间
        private int tokens;             // 当前令牌数量

        public TokenBucket(int capacity, int rate) {
            this.capacity = capacity;
            this.rate = rate;
            this.lastRefillTime = System.nanoTime();
            this.tokens = 0;
        }

        private void refillTokens() {
            long currentTime = System.nanoTime();
            long timePassed = currentTime - lastRefillTime;
            int newTokens = (int) TimeUnit.NANOSECONDS.toSeconds(timePassed) * rate;
            tokens = Math.min(capacity, tokens + newTokens);
            lastRefillTime = currentTime;
        }

        public synchronized boolean request(int tokens) {
            refillTokens();
            if (this.tokens >= tokens) {
                this.tokens -= tokens;
                return true;  // 请求通过
            } else {
                return false;  // 令牌不足，请求被拒绝
            }
        }
    }
}
