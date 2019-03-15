package com.ladtor.workflow.dao.impl.service;

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
}
