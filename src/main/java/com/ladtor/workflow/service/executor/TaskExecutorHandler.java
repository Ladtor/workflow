package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.bo.execute.ExecuteResult;
import com.ladtor.workflow.bo.execute.TaskExecuteInfo;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/12 16:47
 */
@Service
class TaskExecutorHandler extends AbstractExecutorHandler<TaskExecuteInfo> {

    public static final String TASK = "TASK";

    public TaskExecutorHandler() {
        super(TASK);
    }

    public TaskExecutorHandler(String name) {
        super(name);
    }

    @Override
    protected void doExecute(TaskExecuteInfo executeInfo) {

    }

    @Override
    protected void doSuccess(ExecuteResult executeResult) {

    }

    @Override
    protected void doFail(ExecuteResult executeResult) {

    }

    @Override
    protected boolean doCancel(TaskExecuteInfo executeInfo) {
        return false;
    }
}
