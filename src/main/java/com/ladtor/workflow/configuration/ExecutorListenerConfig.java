package com.ladtor.workflow.configuration;

import com.ladtor.workflow.service.executor.listener.ExecutorListenerHandler;
import com.ladtor.workflow.service.executor.listener.GraphExecutorListener;
import com.ladtor.workflow.service.executor.listener.LogExecutorListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liudongrong
 * @date 2019/1/14 23:32
 */
@Configuration
public class ExecutorListenerConfig {
    @Bean
    public ExecutorListenerHandler executorListenerHandler(){
        ExecutorListenerHandler executorListenerHandler = new ExecutorListenerHandler();
        executorListenerHandler.add(new LogExecutorListener());
        executorListenerHandler.add(new GraphExecutorListener());
        return executorListenerHandler;
    }
}
