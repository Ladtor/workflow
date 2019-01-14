package com.ladtor.workflow.service.executor;

/**
 * @author liudongrong
 * @date 2019/1/12 16:42
 */
public interface ExecutorHandler<T> {
    void execute(T executeInfo);

    boolean cancel(T executeInfo);

    void success(T executeInfo);

    void fail(T executeInfo);
}
