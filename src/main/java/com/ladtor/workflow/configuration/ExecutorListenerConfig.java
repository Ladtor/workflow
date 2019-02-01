package com.ladtor.workflow.configuration;

import com.ladtor.workflow.service.executor.listener.ExecutorListenerHandler;
import com.ladtor.workflow.service.executor.listener.GraphExecutorListener;
import com.ladtor.workflow.service.executor.listener.LogExecutorListener;
import com.ladtor.workflow.service.executor.listener.ResultExecutorListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liudongrong
 * @date 2019/1/14 23:32
 */
@Configuration
public class ExecutorListenerConfig {
    @Bean
    public ExecutorListenerHandler executorListenerHandler(LogExecutorListener logExecutorListener, GraphExecutorListener graphExecutorListener, ResultExecutorListener resultExecutorListener){
        ExecutorListenerHandler executorListenerHandler = new ExecutorListenerHandler();
        executorListenerHandler.add(logExecutorListener);
        executorListenerHandler.add(resultExecutorListener);
        executorListenerHandler.add(graphExecutorListener);
        return executorListenerHandler;
    }
}
