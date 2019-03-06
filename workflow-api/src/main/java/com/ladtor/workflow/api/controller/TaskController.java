package com.ladtor.workflow.api.controller;

import com.ladtor.workflow.dao.domain.Task;
import com.ladtor.workflow.core.exception.ClientException;
import com.ladtor.workflow.dao.TaskService;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/2/5 20:23
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private AdminServerProperties adminServerProperties;

    @Autowired
    private TaskService taskService;

    @GetMapping
    @ResponseBody
    public List<Task> tasks(){
        return taskService.list();
    }

    @GetMapping("/{nodeName}")
    @ResponseBody
    public List<Task> tasks(@PathVariable String nodeName) {
        return taskService.selectByNodeName(nodeName);
    }

    @PostMapping("/{nodeName}")
    @ResponseBody
    public void addTask(@PathVariable String nodeName, @RequestBody Task task){
        task.setNode(nodeName);
        if(!taskService.save(task)){
            throw new ClientException("添加失败");
        }
    }

    @GetMapping("/nodes")
    public String nodes() {
        return String.format("forward:%s/applications", adminServerProperties.getContextPath());
    }
}
