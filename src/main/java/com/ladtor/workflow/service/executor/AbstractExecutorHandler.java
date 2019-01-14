package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.bo.execute.ExecuteInfo;
import com.ladtor.workflow.service.executor.listener.ExecutorListener;
import com.ladtor.workflow.service.executor.listener.ExecutorListenerHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/12 16:56
 */
public abstract class AbstractExecutorHandler<T extends ExecuteInfo> implements ExecutorHandler<T> {
    public static final String EXECUTOR_NAME = "ExecutorName";
    private String name;

    @Autowired
    private ExecutorListenerHandler executorListenerHandler;

    public AbstractExecutorHandler(String name) {
        this.name = name;
    }

    protected abstract void doExecute(T executeInfo);

    protected abstract void doSuccess(T executeInfo);

    protected abstract void doFail(T executeInfo);

    protected abstract boolean doCancel(T executeInfo);

    @Override
    public final void success(T executeInfo){
        doSuccess(executeInfo);
        List<ExecutorListener> listeners = executorListenerHandler.getListeners();
        listeners.forEach(listener -> listener.success(name, executeInfo));
        listeners.forEach(listener -> listener.complete(name, executeInfo));
    }

    @Override
    public final void fail(T executeInfo){
        doFail(executeInfo);
        List<ExecutorListener> listeners = executorListenerHandler.getListeners();
        listeners.forEach(listener -> listener.fail(name, executeInfo));
        listeners.forEach(listener -> listener.complete(name, executeInfo));
    }

    @Override
    public final void execute(T executeInfo) {
        List<ExecutorListener> listeners = executorListenerHandler.getListeners();
        listeners.forEach(listener -> listener.before(name, executeInfo));
        doExecute(executeInfo);
    }

    @Override
    public final boolean cancel(T executeInfo) {
        boolean result = doCancel(executeInfo);
        if (result) {
            List<ExecutorListener> listeners = executorListenerHandler.getListeners();
            listeners.forEach(listener -> listener.cancel(name, executeInfo));
        }
        return result;
    }

}
