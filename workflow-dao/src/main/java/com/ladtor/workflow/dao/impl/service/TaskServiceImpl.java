package com.ladtor.workflow.dao.impl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.dao.TaskService;
import com.ladtor.workflow.dao.domain.Task;
import com.ladtor.workflow.dao.impl.mapper.TaskMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Override
    public List<Task> selectByNodeName(String nodeName) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("node", nodeName);
        return this.list(wrapper);
    }

    @Transactional
    @Override
    public boolean save(Task task) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("node", task.getNode())
                .eq("task_key", task.getTaskKey());
        if (super.count(wrapper) == 0) {
            return super.save(task);
        }
        return false;
    }

    @Override
    public List<Task> list() {
        return super.list(null);
    }
}