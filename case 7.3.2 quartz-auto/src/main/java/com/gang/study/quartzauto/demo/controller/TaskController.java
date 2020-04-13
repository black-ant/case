package com.gang.study.quartzauto.demo.controller;

import com.gang.study.quartzauto.demo.entity.TaskEntity;
import com.gang.study.quartzauto.demo.logic.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname TaskController
 * @Description TODO
 * @Date 2020/4/13 20:26
 * @Created by zengzg
 */
@RestController
@RequestMapping(value = "/basic/task")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    @PostMapping(value = "/add")
    public ResponseEntity addTask(@RequestBody TaskEntity taskEntity) {
        return taskService.addTask(taskEntity) ? new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/update")
    public ResponseEntity updateTask(@RequestBody TaskEntity taskEntity) {
        return taskService.updateTask(taskEntity) ? new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/queryJob")
    public ResponseEntity queryJob(TaskEntity taskEntity) {
        List<TaskEntity> taskEntities = taskService.findTaskList(taskEntity);
        return taskEntities.size() == 0 ? new ResponseEntity("Not Exist!", HttpStatus.OK) :
                new ResponseEntity(taskEntities, HttpStatus.OK);
    }


    @PostMapping(value = "/delete")
    public ResponseEntity deleteTask(@RequestBody TaskEntity taskEntity) {
        boolean flag = taskService.deleteTask(taskEntity);
        return flag ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @PostMapping(value = "/pause")
    public ResponseEntity pauseTask(@RequestBody TaskEntity taskEntity) {
        boolean flag = taskService.pauseTask(taskEntity);
        return flag ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @PostMapping(value = "/resume")
    public ResponseEntity resumeTask(@RequestBody TaskEntity taskEntity) {
        boolean flag = taskService.resumeTask(taskEntity);
        return flag ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
