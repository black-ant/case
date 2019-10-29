package para.cic.cloud.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ParaCloudApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ParaCloudApplication.class, args).getBeanFactory();
    }

    public ParaCloudApplication() {
        super();
    }

}
