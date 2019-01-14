package com.ladtor.workflow.service.executor.listener;

import com.ladtor.workflow.bo.execute.ExecuteInfo;

/**
 * @author liudongrong
 * @date 2019/1/12 17:00
 */
public abstract class AbstractExecutorListener implements ExecutorListener {
    @Override
    public void before(String executorName, ExecuteInfo executeInfo) {

    }

    @Override
    public void complete(String executorName, ExecuteInfo executeInfo) {

    }

    @Override
    public void success(String executorName, ExecuteInfo executeInfo) {

    }

    @Override
    public void fail(String executorName, ExecuteInfo executeInfo) {

    }

    @Override
    public void cancel(String executorName, ExecuteInfo executeInfo) {

    }
}
