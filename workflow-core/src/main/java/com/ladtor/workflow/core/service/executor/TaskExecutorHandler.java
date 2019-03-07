package com.ladtor.workflow.core.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;
import com.ladtor.workflow.core.bo.execute.TaskExecuteInfo;
import com.ladtor.workflow.core.service.sender.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/12 16:47
 */
@Service
@Slf4j
class TaskExecutorHandler extends AbstractExecutorHandler<TaskExecuteInfo> {

    @Autowired
    private Sender sender;

    @Autowired
    private Executor executor;

    public static final String TASK = "TASK";

    public TaskExecutorHandler() {
        super(TASK);
    }

    public TaskExecutorHandler(String name) {
        super(name);
    }

    @Override
    protected void doExecute(TaskExecuteInfo executeInfo) {
        try {
            sender.send(executeInfo.getFourTuple(),
                    executeInfo.getTaskNodeKey(),
                    executeInfo.getTaskKey(),
                    executeInfo.getParams());
        } catch (Exception e) {
            log.error("task execute error", e);
            JSONObject result = new JSONObject();
            result.put("message", e.getMessage());
            executor.fail(buildExecuteResult(executeInfo, result));
        }
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
