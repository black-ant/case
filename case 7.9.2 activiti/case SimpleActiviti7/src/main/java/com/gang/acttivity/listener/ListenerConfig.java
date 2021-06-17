package com.gang.acttivity.listener;

import org.activiti.api.model.shared.event.RuntimeEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname ListenerConfig
 * @Description TODO
 * @Date 2021/6/17
 * @Created by zengzg
 */
@Configuration
public class ListenerConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 创建一个 Listener 监听器
     *
     * @return
     */
    @Bean
    public TaskRuntimeEventListener taskAssignedListener() {
        return new TaskRuntimeEventListener() {
            @Override
            public void onEvent(RuntimeEvent event) {
                logger.info(
                        ">>> Task Assigned: '"
                                + event.getEntity()
                                + "' We can send a notification to the assignee: "
                                + event.getId());
            }
        };

    }
}
