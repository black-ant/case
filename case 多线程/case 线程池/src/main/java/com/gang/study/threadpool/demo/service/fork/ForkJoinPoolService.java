package com.gang.study.threadpool.demo.service.fork;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Classname ForkJoinPoolService
 * @Description TODO
 * @Date 2019/11/1 11:38
 * @Created by ant-black 1016930479@qq.com
 */
public class ForkJoinPoolService extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2; //阀值
    private int start;
    private int end;

    public ForkJoinPoolService(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int middle = (start + end) / 2;
            ForkJoinPoolService leftTask = new ForkJoinPoolService(start, middle);
            ForkJoinPoolService rightTask = new ForkJoinPoolService(middle + 1, end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();
            //等待子任务执行完，并得到其结果
            Integer rightResult = rightTask.join();
            Integer leftResult = leftTask.join();
            //合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }



}
