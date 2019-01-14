package com.ladtor.workflow.service.executor.listener;

import com.ladtor.workflow.bo.execute.ExecuteInfo;

/**
 * @author liudongrong
 * @date 2019/1/12 16:57
 */
public interface ExecutorListener {
    void before(String executorName, ExecuteInfo executeInfo);
    void complete(String executorName, ExecuteInfo executeInfo);
    void success(String executorName, ExecuteInfo executeInfo);
    void fail(String executorName, ExecuteInfo executeInfo);
    void cancel(String executorName, ExecuteInfo executeInfo);
}
