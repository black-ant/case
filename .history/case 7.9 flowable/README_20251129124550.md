# Flowable Demo

## 项目说明

Spring Boot Flowable 工作流引擎示例，演示 BPMN 2.0 流程建模和执行。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Flowable 6.8.0
- H2 Database

## 什么是 Flowable？

Flowable 是一个轻量级的业务流程引擎，支持 BPMN 2.0、CMMN、DMN 规范。

## 核心概念

| 概念 | 说明 |
|------|------|
| Process Definition | 流程定义（BPMN 模型） |
| Process Instance | 流程实例（运行中的流程） |
| Task | 任务（人工/自动） |
| Execution | 执行实例 |
| Variable | 流程变量 |
| Gateway | 网关（分支/合并） |

## BPMN 元素

| 元素 | 符号 | 说明 |
|------|------|------|
| Start Event | ○ | 流程开始 |
| End Event | ◉ | 流程结束 |
| User Task | □ | 人工任务 |
| Service Task | ⚙ | 自动任务 |
| Exclusive Gateway | ◇ | 排他网关 |
| Parallel Gateway | ⊕ | 并行网关 |

## 项目结构

```
src/main/java/com/gang/flowable/demo/
├── DemoApplication.java              # 启动类
├── config/
│   └── BeanConfig.java               # 配置类
├── controller/
│   └── RunFlowable.java              # 流程控制器
└── service/
    ├── FlowableRepositoryService.java  # 流程仓库服务
    ├── FlowableRunService.java         # 流程运行服务
    └── callback/
        └── FlowableCallback.java       # 流程回调

src/main/resources/
├── holiday-request.bpmn20.xml        # 流程定义文件
└── application.yml
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

## 代码示例

### 部署流程

```java
@Autowired
private RepositoryService repositoryService;

public void deploy() {
    repositoryService.createDeployment()
        .addClasspathResource("processes/holiday-request.bpmn20.xml")
        .deploy();
}
```

### 启动流程

```java
@Autowired
private RuntimeService runtimeService;

public void startProcess(String employee, int days) {
    Map<String, Object> variables = new HashMap<>();
    variables.put("employee", employee);
    variables.put("nrOfHolidays", days);
    
    runtimeService.startProcessInstanceByKey("holidayRequest", variables);
}
```

### 处理任务

```java
@Autowired
private TaskService taskService;

public void completeTasks() {
    List<Task> tasks = taskService.createTaskQuery()
        .taskCandidateGroup("managers")
        .list();
    
    for (Task task : tasks) {
        taskService.complete(task.getId());
    }
}
```

## BPMN 流程示例

```xml
<process id="holidayRequest" name="Holiday Request">
    <startEvent id="startEvent"/>
    <sequenceFlow sourceRef="startEvent" targetRef="approveTask"/>
    
    <userTask id="approveTask" name="Approve or reject request" 
              flowable:candidateGroups="managers"/>
    <sequenceFlow sourceRef="approveTask" targetRef="decision"/>
    
    <exclusiveGateway id="decision"/>
    <sequenceFlow sourceRef="decision" targetRef="approved">
        <conditionExpression>${approved}</conditionExpression>
    </sequenceFlow>
    
    <endEvent id="approved"/>
</process>
```

## 学习资源

- [Flowable 官方文档](https://www.flowable.com/open-source/docs/)
- [BPMN 2.0 规范](https://www.omg.org/spec/BPMN/2.0/)

