package boot.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan("boot.spring.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 参考地址 : https://gitee.com/shenzhanwang/Spring-activiti
     * 登录页 : http://localhost:8888/login -> 用户名xiaomi，密码1234
     * swagger 入口 : http://localhost:8888/swagger-ui.html
     * 流程设计器 :　http://localhost:8080/activiti-explorer
     */


    /**
     * 请求流程 :
     * 1-  http://localhost:8888/startleave
     */

}
