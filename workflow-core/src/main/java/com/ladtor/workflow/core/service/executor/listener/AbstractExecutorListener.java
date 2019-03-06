package com.ladtor.workflow.core.service.executor.listener;

import com.ladtor.workflow.core.bo.execute.ExecuteInfo;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;

/**
 * @author liudongrong
 * @date 2019/1/12 17:00
 */
public abstract class AbstractExecutorListener implements ExecutorListener {
    @Override
    public void before(String executorName, ExecuteInfo executeInfo) {

    }

    @Override
    public void complete(String executorName, ExecuteResult executeResult) {

    }

    @Override
    public void success(String executorName, ExecuteResult executeResult) {

    }

    @Override
    public void fail(String executorName, ExecuteResult executeResult) {

    }

    @Override
    public void cancel(String executorName, ExecuteInfo executeInfo) {

    }
}
