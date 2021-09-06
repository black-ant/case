package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

/**
 * @Classname Num_233_NumberofDigitOne
 * @Description TODO
 * @Date 2021/8/20
 * @Created by zengzg
 */
public class Num_233_NumberofDigitOne extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_233_NumberofDigitOne().run();
    }


    @Override
    public void run() {
        logger.info("------> {} <-------", countDigitOne(99));
    }

    public int countDigitOne(int n) {
        int count = 0;

        for (long k = 1; k <= n; k *= 10) {
            long r = n / k, m = n % k;
            // sum up the count of ones on every place k
            count += (r + 8) / 10 * k + (r % 10 == 1 ? m + 1 : 0);
        }

        return count;
    }
}
