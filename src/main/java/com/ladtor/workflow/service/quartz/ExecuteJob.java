package com.ladtor.workflow.service.quartz;

import com.ladtor.workflow.bo.execute.ExecuteInfo;
import com.ladtor.workflow.service.executor.ExecutorFactory;
import com.ladtor.workflow.service.executor.ExecutorHandler;
import lombok.Setter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author liudongrong
 * @date 2019/2/2 13:24
 */
public class ExecuteJob implements Job {
    public static final String EXECUTE_INFO = "executeInfo";

    @Setter
    private ExecuteInfo executeInfo;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ExecutorHandler executorHandler = ExecutorFactory.getExecutorHandler(executeInfo.getNodeType());
        if (executorHandler != null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorHandler.execute(executeInfo);
        }
    }
}
