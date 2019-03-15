package com.ladtor.workflow.api.controller;

import com.ladtor.workflow.core.exception.ClientException;
import com.ladtor.workflow.dao.TaskApplicationService;
import com.ladtor.workflow.dao.TaskService;
import com.ladtor.workflow.dao.domain.Task;
import com.ladtor.workflow.dao.domain.TaskApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/2/5 20:23
 */
@RestController
@RequestMapping("/taskApplication")
public class TaskApplicationController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskApplicationService taskApplicationService;

//    @GetMapping
//    public List<Task> tasks(){
//        return taskService.list();
//    }

    @GetMapping("/{nodeName}")
    public List<Task> tasks(@PathVariable String nodeName) {
        return taskService.selectByNodeName(nodeName);
    }

    @PostMapping("/{nodeName}")
    public void addTask(@PathVariable String nodeName, @RequestBody Task task){
        task.setNode(nodeName);
        if(!taskService.save(task)){
            throw new ClientException("添加失败");
        }
    }

    @GetMapping
    public List<TaskApplication> nodes() {
        return taskApplicationService.list();
    }

    @PostMapping
    public boolean addNode(@RequestBody TaskApplication taskApplication) {
        return taskApplicationService.save(taskApplication);
    }
}
