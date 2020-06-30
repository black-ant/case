package com.gang.study.spring.batchsample.demo.config;

import com.gang.study.spring.batchsample.demo.logic.MyTaskOne;
import com.gang.study.spring.batchsample.demo.logic.MyTaskTwo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname BatchConfig
 * @Description TODO
 * @Date 2020/6/30 15:44
 * @Created by zengzg
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Step stepOne() {
        return steps.get("stepOne")
                .tasklet(new MyTaskOne())
                .build();
    }

    @Bean
    public Step stepTwo() {
        return steps.get("stepTwo")
                .tasklet(new MyTaskTwo())
                .build();
    }

    @Bean
    public Job demoJob() {
        return jobs.get("demoJob")
                .incrementer(new RunIdIncrementer())
                .start(stepOne())
                .next(stepTwo())
                .build();
    }
}
