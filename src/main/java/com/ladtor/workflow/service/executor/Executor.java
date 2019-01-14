package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.bo.execute.ExecuteInfo;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/12 21:24
 */
@Service
public class Executor implements ExecutorHandler<ExecuteInfo> {

    @Override
    public void execute(ExecuteInfo executeInfo) {
        ExecutorHandler executorHandler = ExecutorFactory.getExecutorHandler(executeInfo);
        if (executorHandler != null) {
            executorHandler.execute(executeInfo);
        }
    }

    @Override
    public boolean cancel(ExecuteInfo executeInfo) {
        ExecutorHandler executorHandler = ExecutorFactory.getExecutorHandler(executeInfo);
        if (executorHandler != null) {
            executorHandler.cancel(executeInfo);
        }
        return false;
    }

    @Override
    public void success(ExecuteInfo executeInfo) {
        ExecutorHandler executorHandler = ExecutorFactory.getExecutorHandler(executeInfo);
        if (executorHandler != null) {
            executorHandler.success(executeInfo);
        }
    }

    @Override
    public void fail(ExecuteInfo executeInfo) {
        ExecutorHandler executorHandler = ExecutorFactory.getExecutorHandler(executeInfo);
        if (executorHandler != null) {
            executorHandler.fail(executeInfo);
        }
    }
}
