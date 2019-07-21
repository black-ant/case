package com.gang.flowable.demo.service;


import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class FlowableRunService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProcessEngine processEngine;

    private Scanner scanner;

    private Task task;

    private Map<String, Object> variables;

    private TaskService taskService;

    @Autowired
    FlowableRepositoryService flowableRepositoryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> flowable Service is run okkkkkkkkk <-------");
    }

    public void start() {
        logger.info("===============flowable is start");
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();
        init(processEngine);
        run();
    }

    public void init(ProcessEngine processEngine) {
        this.processEngine = processEngine;
        taskService = processEngine.getTaskService();
    }

    public void run() {
        logger.info("创建实例");
        flowableRepositoryService.init(processEngine);

        logger.info("检查实例是否成功");
        flowableRepositoryService.check();

        scanner = new Scanner(System.in);

        System.out.println("Who are you?");
        String employee = scanner.nextLine();

        System.out.println("How many holidays do you want to request?");
        Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

        System.out.println("Why do you need them?");
        String description = scanner.nextLine();


        RuntimeService runtimeService = processEngine.getRuntimeService();
        variables = new HashMap<String, Object>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);

        logger.info("-----> 获得任务列表 <--------");
        List<Task> tasks = getActualTask();

        logger.info("------> 获得最终处理结果 <-------");
        getActualResult(tasks);

        logger.info("------> 经理去处理相关的任务 <-------");
        ManageDoSomething();

        logger.info("------> 展示历史数据 <-------");
        showLocalHistory(processInstance);
    }

    /**
     * 获得实际的任务列表
     */
    public List<Task> getActualTask() {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        logger.info("------> " + "You have " + tasks.size() + " tasks:" + " <-------");
        for (int i = 0; i < tasks.size(); i++) {
            logger.info("------> " + (i + 1) + ") " + tasks.get(i).getName() + " <-------");
        }
        return tasks;
    }

    /**
     * 获得最终处理结果
     */
    public void getActualResult(List<Task> tasks) {
        logger.info("------> Which task would you like to complete? <-------");
        int taskIndex = Integer.valueOf(scanner.nextLine());
        task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        logger.info(processVariables.get("employee") + " wants " +
                processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");

    }

    /**
     * 经理完成相关的任务
     */
    public void ManageDoSomething() {

        TaskService taskService = processEngine.getTaskService();
        // --> 判断是否同意
        boolean approved = scanner.nextLine().toLowerCase().equals("1");
        variables = new HashMap<String, Object>();
        variables.put("approved", approved);
        taskService.complete(task.getId(), variables);
    }

    /**
     *
     */
    public void showLocalHistory(ProcessInstance processInstance) {
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities =
                historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .finished()
                        .orderByHistoricActivityInstanceEndTime().asc()
                        .list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId() + " took "
                    + activity.getDurationInMillis() + " milliseconds");
        }
    }
}
