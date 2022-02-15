# Saga 使用案例


## Step 1  :建立连接

@SpringBootApplication
@EnableOmega
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}


## Step 2 : 开启 Saga
@SagaStart(timeout=10)



## Step 3 : 配置补偿方法
@Compensable(timeout=5, compensationMethod="cancel")



http://servicecomb.apache.org/cn/docs/distributed-transaction-of-services-1/
