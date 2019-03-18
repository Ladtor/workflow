package com.ladtor.workflow.api.controller;

import com.ladtor.workflow.dao.TaskApplicationService;
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
    private TaskApplicationService taskApplicationService;

    @GetMapping
    public List<TaskApplication> list() {
        return taskApplicationService.list();
    }

    @PostMapping
    public boolean save(@RequestBody TaskApplication taskApplication) {
        return taskApplicationService.save(taskApplication);
    }
}
