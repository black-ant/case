package com.example.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AgentWorkflowDemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AgentWorkflowDemoApplication.class, args);
    }
}
