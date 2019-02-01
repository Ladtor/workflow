package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.bo.execute.ExecuteResult;

/**
 * @author liudongrong
 * @date 2019/1/12 16:42
 */
public interface ExecutorHandler<T> {
    void execute(T executeInfo);

    boolean cancel(T executeInfo);

    void success(ExecuteResult executeResult);

    void fail(ExecuteResult executeResult);
}
