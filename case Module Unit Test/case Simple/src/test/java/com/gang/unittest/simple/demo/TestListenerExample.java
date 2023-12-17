package com.gang.unittest.simple.demo;

import org.springframework.test.context.TestExecutionListeners;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @Classname TestListenerExample
 * @Description TODO
 * @Date 2022/12/3
 * @Created by zengzg
 */
@TestExecutionListeners(
        listeners = {},
        inheritListeners = false,
        mergeMode = MERGE_WITH_DEFAULTS)
public class TestListenerExample {
}
