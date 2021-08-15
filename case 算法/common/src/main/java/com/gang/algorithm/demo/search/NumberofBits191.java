package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

/**
 * @Classname NumberofBits191
 * @Description TODO
 * @Date 2021/8/15
 * @Created by zengzg
 */
public class NumberofBits191 extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new NumberofBits191().run();
    }


    @Override
    public void run() {
        int n = 100;
        // “a”的值是129，转换成二进制就是10000001，而“b”的值是128，转换成二进制就是10000000。根据与运算符的运算规律，只有两个位都是1，结果才是1
        logger.info("------> {} <-------", n & 1);
    }

    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            count += n & 1;
            n >>>= 1;
        }
        return count;
    }


    public int hammingWeight1(int n) {
        int count = 0;
        while (n != 0) {
            // 然后 n & (n-1) 的结果就是把 n 的最右边的 1 置为 0
            // PS " 减一是要向前借位的
            n &= (n - 1);
            count += 1;
        }
        return count;
    }


    public int hammingWeight3(int n) {
        n = (n & 0x55555555) + ((n >>> 1) & 0x55555555); // 32 组向 16 组合并，合并前每组 1 个数
        n = (n & 0x33333333) + ((n >>> 2) & 0x33333333); // 16 组向 8 组合并，合并前每组 2 个数
        n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f); // 8 组向 4 组合并，合并前每组 4 个数
        n = (n & 0x00ff00ff)+ ((n >>> 8) & 0x00ff00ff); // 4 组向 2 组合并，合并前每组 8 个数
        n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff); // 2 组向 1 组合并，合并前每组 16 个数
        return n;
    }
}
