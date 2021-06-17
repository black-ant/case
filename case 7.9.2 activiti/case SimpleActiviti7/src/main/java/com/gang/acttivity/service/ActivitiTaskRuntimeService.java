package com.gang.acttivity.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.acttivity.config.SecurityUtil;
import org.activiti.api.runtime.shared.NotFoundException;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.CreateTaskPayloadBuilder;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.model.payloads.CreateTaskPayload;
import org.activiti.api.task.model.payloads.ReleaseTaskPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Classname ActivitiService
 * TaskRuntime API :
 * Task task(String taskId) : 通过id获取任务
 * Page<Task> tasks(Pageable pageable); 获取当前认证用户所有的 Task
 * Page<Task> tasks(Pageable pageable,GetTasksPayload getTasksPayload) : 获取在Payload中应用筛选器的所有任务
 * Task create(CreateTaskPayload createTaskPayload) : 创建 Task
 * Task claim(ClaimTaskPayload claimTaskPayload) : 声明一个 Task
 * Task release(ReleaseTaskPayload releaseTaskPayload) : 发布一个先前声明的任务
 * Task complete(CompleteTaskPayload completeTaskPayload) : 在有效负载中设置变量来完成选定的任务
 * Task update(UpdateTaskPayload updateTaskPayload) : 更新任务详细信息
 * Task delete(DeleteTaskPayload deleteTaskPayload) : 删除任务
 * @Date 2021/6/17
 * @Created by zengzg
 */
@Component
public class ActivitiTaskRuntimeService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String taskId = "MyTask001";

    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> [开启一个完整的 Activiti 流程] , 首先模拟登录一个用户 <-------");

        // PS : Activiti 默认依赖 Spring Security
        securityUtil.logInAs("root");

        deleteTask();

        // 创建流程
        createTask();

        // 查询对象
        getTaskInfo();
        selectTaskInfo();

        selectTaskInfoByUserId("admin");
        selectTaskInfoByUserId("root");

        // 执行流程
        doTask();

        // 再次查询流程
        getTaskInfo();
        selectTaskInfo();
    }


    public void getTaskInfo() {
        try {
            // Step 2 : 查询单个 Task 信息
            Task temTask = taskRuntime.task(taskId);
            logger.info("------> Step 2  查询 ID : {} - 对应的 Task :{} <-------", taskId, JSONObject.toJSONString(temTask));
        } catch (NotFoundException e) {
            logger.error("E----> 当前 Task 已经处理完成 , 未查询到 error :{} -- content :{}", e.getClass(), e.getMessage());
        }
    }

    /**
     * 查询当前的 Task 案例
     */
    public void selectTaskInfo() {
        // Step 2 : 查询已知的所有的 Task 信息
        Pageable pageable = Pageable.of(0, 10);
        Page<Task> temTaskList = taskRuntime.tasks(pageable);
        temTaskList.getContent().forEach(item -> {
            logger.info("------> Step 2-1  查询系列数量 - [{}] - 对应的 Task :{}  <-------", temTaskList.getTotalItems(), JSONObject.toJSONString(item));
        });

    }

    /**
     * 对应委托人查询自己的任务
     */
    public void selectTaskInfoByUserId(String assignee) {
        // Step 2 : 查询已知的所有的 Task 信息
        Pageable pageable = Pageable.of(0, 10);
        Page<Task> temTaskList = taskRuntime.tasks(pageable, TaskPayloadBuilder.tasks().withAssignee(assignee).build());
        temTaskList.getContent().forEach(item -> {
            logger.info("------> Step 2-2  查询 assignee :{} 系列数量 - [{}] - 对应的 Task :{}  <-------", assignee, temTaskList.getTotalItems(), JSONObject.toJSONString(item));
        });

    }


    /**
     * 创建一个 Task 任务
     */
    public void createTask() {
        logger.info("------> Step 1 : 创建一个 Task 开始 <-------");
        CreateTaskPayload taskPayloadBuilder = TaskPayloadBuilder.create()
                .withName("First Team Task")
                .withDescription("This is something really important")
                // 设置当前 Task 的用户组
                .withGroup("ADMIN")
                .withPriority(10)
                .build();
        Task temTask = taskRuntime.create(taskPayloadBuilder);

        logger.info("------> Step 1 创建第二个 Task , 注意 , 此处设置了 Assignee  <-------");
        CreateTaskPayload taskPayloadBuilderTo = TaskPayloadBuilder.create()
                .withName("Second Team Task")
                .withDescription("This is something really important hava Assignee")
                // 设置当前 Task 的用户组
                .withGroup("ADMIN")
                .withAssignee("admin")
                .withPriority(10)
                .build();
        taskRuntime.create(taskPayloadBuilderTo);


        this.taskId = temTask.getId();
    }

    /**
     * 执行一个 Task
     */
    public void doTask() {

        logger.info("------> Step 3-1 : 声明一个 Task 开始 claimed <-------");
        taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(taskId).build());

//        logger.info("------> Step 3-2 : 发布一个 Task 开始 release <-------");
//        ReleaseTaskPayload releaseTaskPayload = new ReleaseTaskPayload();
//        releaseTaskPayload.setTaskId(taskId);
//        taskRuntime.release(releaseTaskPayload);

        logger.info("------> Step 3-3 : 完成一个 Task 开始 complete <-------");
        taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskId).build());
    }

    /**
     * 删除 Task : PS : 注意 , 删除也需要权限
     */
    public void deleteTask() {
        logger.info("------> Step 4 : 删除所有的Task <-------");

        Pageable pageable = Pageable.of(0, 10);
        Page<Task> temTaskList = taskRuntime.tasks(pageable);
        temTaskList.getContent().forEach(item -> {
            try {
                logger.info("------> Step 4 item : 删除Task :{} <-------", item.getId());
                taskRuntime.delete(TaskPayloadBuilder.delete().withTaskId(item.getId()).build());
            } catch (Exception e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            }

        });

        securityUtil.logInAs("admin");
        Pageable pageableAdmin = Pageable.of(0, 10);
        Page<Task> temTaskListAdmin = taskRuntime.tasks(pageable);
        temTaskListAdmin.getContent().forEach(item -> {
            try {
                logger.info("------> Step 4 item : 删除Task :{} <-------", item.getId());
                taskRuntime.delete(TaskPayloadBuilder.delete().withTaskId(item.getId()).build());
            } catch (Exception e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            }
        });

        securityUtil.logInAs("root");


    }
}
