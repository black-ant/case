package net.gang.bundles.demo;

import net.gang.bundles.demo.service.BundlesOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private BundlesOp bundlesOp;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        bundlesOp.init();
    }
}
