package com.ladtor.workflow.core.service.executor.listener;

import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/14 23:31
 */
public class ExecutorListenerHandler {
    private List<ExecutorListener> listeners = Lists.newArrayList();

    public void add(ExecutorListener listener) {
        listeners.add(listener);
    }

    public void remove(ExecutorListener listener) {
        listeners.remove(listener);
    }

    public List<ExecutorListener> getListeners() {
        return Lists.newArrayList(listeners.iterator());
    }
}
