# Quartz Demo

## 项目说明

Spring Boot Quartz 定时任务调度示例，演示企业级任务调度。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Quartz Scheduler
- H2 Database (任务持久化)

## Quartz 核心概念

| 概念 | 说明 |
|------|------|
| Job | 任务接口，定义要执行的业务逻辑 |
| JobDetail | 任务详情，包含 Job 类和配置 |
| Trigger | 触发器，定义执行时机 |
| Scheduler | 调度器，管理所有任务 |
| JobStore | 任务存储（内存/数据库） |

## 触发器类型

| 类型 | 说明 | 使用场景 |
|------|------|----------|
| SimpleTrigger | 简单触发器 | 固定间隔执行 |
| CronTrigger | Cron 表达式 | 复杂调度规则 |
| CalendarIntervalTrigger | 日历间隔 | 按日历间隔 |
| DailyTimeIntervalTrigger | 每日时间间隔 | 工作日调度 |

## Cron 表达式

```
秒 分 时 日 月 周 [年]
 *  *  *  *  *  *
```

| 示例 | 说明 |
|------|------|
| `0 0 12 * * ?` | 每天12点 |
| `0 0/5 * * * ?` | 每5分钟 |
| `0 0 8-18 * * MON-FRI` | 工作日8-18点每小时 |

## 项目结构

```
src/main/java/com/gang/study/quartz/demo/
├── DemoApplication.java          # 启动类
├── config/
│   ├── QuartzConfig.java         # Quartz 配置
│   └── SchedulerConfig.java      # 调度器配置
├── logic/
│   ├── TestJob.java              # 测试任务
│   └── CronQuartzJobBean.java    # Cron 任务
├── service/
│   └── JobServiceManager.java    # 任务管理服务
└── controller/
    └── ManagerController.java    # 任务管理接口
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

## 代码示例

### 定义 Job

```java
public class MyJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("Job executed at: {}", LocalDateTime.now());
    }
}
```

### 配置 Trigger

```java
@Bean
public JobDetail jobDetail() {
    return JobBuilder.newJob(MyJob.class)
            .withIdentity("myJob")
            .storeDurably()
            .build();
}

@Bean
public Trigger trigger(JobDetail jobDetail) {
    return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity("myTrigger")
            .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
            .build();
}
```

## 学习资源

- [Quartz 官方文档](http://www.quartz-scheduler.org/documentation/)
- [Spring Quartz 集成](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.quartz)

