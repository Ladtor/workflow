package com.ladtor.workflow.api.controller;

import com.ladtor.workflow.core.exception.ClientException;
import com.ladtor.workflow.dao.TaskService;
import com.ladtor.workflow.dao.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> list(){
        return taskService.list();
    }

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
}
