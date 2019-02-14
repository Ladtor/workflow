package com.ladtor.workflow.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.bo.domain.Task;
import com.ladtor.workflow.mapper.TaskMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/2/7 21:07
 */
@Service
public class TaskService extends ServiceImpl<TaskMapper, Task> {

    public List<Task> selectByNodeName(String nodeName) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("node", nodeName);
        return this.list(wrapper);
    }

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
}
