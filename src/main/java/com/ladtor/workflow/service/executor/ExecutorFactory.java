package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.bo.execute.ExecuteInfo;
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

    public static ExecutorHandler getExecutorHandler(ExecuteInfo executeInfo){
        switch (executeInfo.getNodeType()) {
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
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
