package com.ladtor.workflow.core.service.executor;

import com.ladtor.workflow.core.bo.Node;
import com.ladtor.workflow.common.constant.NodeType;
import com.ladtor.workflow.core.bo.execute.ExecuteInfo;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.core.exception.NotSuchExecutorException;
import com.ladtor.workflow.core.service.wrapper.WorkFlowWrapper;
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
    private static WorkFlowWrapper workFlowWrapper;

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
            case MANUAL:
                return applicationContext.getBean(ManualExecuteHandler.class);
        }
        throw new NotSuchExecutorException(nodeType.toString());
    }

    public static ExecuteInfo getExecuteInfo(FourTuple fourTuple) {
        Node node = workFlowWrapper.getNode(fourTuple);
        return node.getExecuteInfo(fourTuple);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.workFlowWrapper = applicationContext.getBean(WorkFlowWrapper.class);
    }
}
