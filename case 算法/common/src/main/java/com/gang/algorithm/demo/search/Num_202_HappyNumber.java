package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname Num_202_HappyNumber
 * @Description TODO
 * @Date 2021/8/18
 * @Created by zengzg
 */
public class Num_202_HappyNumber extends AbstractAlgorithmService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        new Num_202_HappyNumber().run();
    }

    /**
     * 平方和最后等于1 就是快乐
     */
    @Override
    public void run() {
        logger.info("------> [{}] <-------", isHappy(19));
    }

    /**
     * 快慢指针问题
     * 这里的问题是可能到 1 , 也可能陷入其他的死循环
     * @param n
     * @return
     */
    public boolean isHappy(int n) {
        int slow = n;
        int fast = n;
        do {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
            logger.info("------> slow [{}] - fast [{}]  <-------", slow, fast);
        } while (slow != fast);
        return slow == 1;
    }

    private int getNext(int n) {
        int next = 0;
        while (n > 0) {
            int t = n % 10;
            next += t * t;
            n /= 10;
        }
        return next;
    }


}
