package com.gang.flowable.demo.service;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import static org.flowable.engine.ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream;
import static org.flowable.engine.ProcessEngineConfiguration.createProcessEngineConfigurationFromResource;

@Service
public class FlowableRepositoryService {

    private RepositoryService repositoryService;
    private Deployment deployment;
    private ProcessEngine processEngine;

    public void init(ProcessEngine processEngine) {
        this.processEngine = processEngine;
        repositoryService = processEngine.getRepositoryService();
        deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();
    }

    public void check() {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());
    }

    /**
     * 通过 xml 文件创建
     */
    public void createByXml() {
//        ProcessEngineConfiguration.
//                createProcessEngineConfigurationFromResourceDefault();
//        createProcessEngineConfigurationFromResource(String resource);
//        createProcessEngineConfigurationFromResource(String resource, String beanName);
//        createProcessEngineConfigurationFromInputStream(InputStream inputStream);
//        createProcessEngineConfigurationFromInputStream(InputStream inputStream, String beanName);

    }
}
