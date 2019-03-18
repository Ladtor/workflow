package com.ladtor.workflow.dao.impl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.dao.TaskApplicationService;
import com.ladtor.workflow.dao.domain.TaskApplication;
import com.ladtor.workflow.dao.impl.mapper.TaskApplicationMapper;

import java.util.List;

public class TaskApplicationServiceImpl extends ServiceImpl<TaskApplicationMapper, TaskApplication> implements TaskApplicationService {

    @Override
    public List<TaskApplication> list() {
        return super.list(null);
    }

    @Override
    public TaskApplication get(String name) {
        QueryWrapper<TaskApplication> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return super.getOne(wrapper);
    }
}
