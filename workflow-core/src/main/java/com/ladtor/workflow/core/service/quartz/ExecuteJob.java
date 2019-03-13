package com.ladtor.workflow.core.service.quartz;

import com.ladtor.workflow.core.bo.execute.ExecuteInfo;
import com.ladtor.workflow.core.service.executor.ExecutorFactory;
import com.ladtor.workflow.core.service.executor.ExecutorHandler;
import lombok.Setter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @author liudongrong
 * @date 2019/2/2 13:24
 */
public class ExecuteJob implements Job {
    public static final String EXECUTE_INFO = "executeInfo";

    @Setter
    private ExecuteInfo executeInfo;

    @Override
    public void execute(JobExecutionContext context) {
        ExecutorHandler executorHandler = ExecutorFactory.getExecutorHandler(executeInfo.getNodeType());
        executorHandler.execute(executeInfo);
    }
}
