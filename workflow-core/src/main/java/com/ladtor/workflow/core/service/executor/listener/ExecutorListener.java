package com.ladtor.workflow.core.service.executor.listener;

import com.ladtor.workflow.core.bo.execute.ExecuteInfo;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;

/**
 * @author liudongrong
 * @date 2019/1/12 16:57
 */
public interface ExecutorListener {
    void before(String executorName, ExecuteInfo executeInfo);
    void complete(String executorName, ExecuteResult executeResult);
    void success(String executorName, ExecuteResult executeResult);
    void fail(String executorName, ExecuteResult executeResult);
    void cancel(String executorName, ExecuteInfo executeInfo);
}
