package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.bo.execute.*;
import com.ladtor.workflow.constant.NodeType;
import com.ladtor.workflow.service.WorkFlowService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/12 21:26
 */
@Service
public class ExecutorFactory implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static WorkFlowService workFlowService;

    public static ExecutorHandler getExecutorHandler(NodeType nodeType){
        switch (nodeType) {
            case WORK_FLOW:
                return applicationContext.getBean(WorkFlowExecutorHandler.class);
            case TASK:
                return applicationContext.getBean(TaskExecutorHandler.class);
            case HTTP:
                return applicationContext.getBean(HttpExecutorHandler.class);
            case START:
                return applicationContext.getBean(StartExecutorHandler.class);
            case RESULT:
                return applicationContext.getBean(ResultExecuteHandler.class);
            case OR:
                return applicationContext.getBean(OrExecuteHandler.class);
            case AND:
                return applicationContext.getBean(AndExecuteHandler.class);
        }
        return null;
    }

    public static ExecuteInfo getExecuteInfo(FourTuple fourTuple) {
        Node node = workFlowService.getNode(fourTuple);
        return node.getExecuteInfo(fourTuple);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.workFlowService = applicationContext.getBean(WorkFlowService.class);
    }
}
