package com.ladtor.workflow.dao;

import com.ladtor.workflow.dao.domain.TaskApplication;

import java.util.List;

public interface TaskApplicationService {
    boolean save(TaskApplication taskApplication);

    List<TaskApplication> list();
}
