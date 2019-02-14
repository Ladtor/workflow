package com.ladtor.workflow.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.GraphBo;
import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.bo.WorkFlowBo;
import com.ladtor.workflow.bo.execute.ExecuteInfo;
import com.ladtor.workflow.bo.execute.ExecuteResult;
import com.ladtor.workflow.bo.execute.FourTuple;
import com.ladtor.workflow.bo.execute.StartExecuteInfo;
import com.ladtor.workflow.service.NodeLogService;
import com.ladtor.workflow.service.WorkFlowService;
import com.ladtor.workflow.service.quartz.ExecuteJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/12 21:24
 */
@Service
@Slf4j
public class Executor implements ExecutorHandler<ExecuteInfo> {
    public static final String PARENT_KEY = "parent";

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private NodeLogService nodeLogService;

    @Autowired
    private Scheduler scheduler;

    public void execute(String serialNo, JSONObject params){
        WorkFlowBo workFlow = workFlowService.getWorkFlow(serialNo);
        GraphBo graph = workFlow.getGraph();
        Node startNode = graph.getStartNode();

        Integer runVersion = workFlowService.createRunVersion(serialNo, workFlow.getVersion(), params);

        StartExecuteInfo startExecuteInfo = ((StartExecuteInfo) graph.getExecuteInfo(startNode, runVersion));
        startExecuteInfo.setInitParams(params);
        this.execute(startExecuteInfo);
    }

    @Override
    public void execute(ExecuteInfo executeInfo) {
        this.execute(executeInfo, true);
    }

    public void execute(ExecuteInfo executeInfo, boolean async) {
        if (async) {
            JobDetail jobDetail = getJobDetail(executeInfo);
            Trigger trigger = getTrigger(executeInfo);
            try {
                if (nodeLogService.setPending(executeInfo.getFourTuple()) && !scheduler.checkExists(jobDetail.getKey())) {
                    scheduler.scheduleJob(jobDetail, trigger);
                }
            } catch (SchedulerException e) {
                log.error("SchedulerException ", e);
            }
        } else {
            ExecutorHandler executorHandler = ExecutorFactory.getExecutorHandler(executeInfo.getNodeType());
            executorHandler.execute(executeInfo);
        }
    }

    @Override
    public boolean cancel(ExecuteInfo executeInfo) {
        ExecutorHandler executorHandler = ExecutorFactory.getExecutorHandler(executeInfo.getNodeType());
        if (executorHandler != null) {
            return executorHandler.cancel(executeInfo);
        }
        return false;
    }

    @Override
    public void success(ExecuteResult executeResult) {
        ExecutorHandler executorHandler = ExecutorFactory.getExecutorHandler(executeResult.getNodeType());
        if (executorHandler != null) {
            executorHandler.success(executeResult);
        }
    }

    @Override
    public void fail(ExecuteResult executeResult) {
        ExecutorHandler executorHandler = ExecutorFactory.getExecutorHandler(executeResult.getNodeType());
        if (executorHandler != null) {
            executorHandler.fail(executeResult);
        }
    }

    private JobDetail getJobDetail(ExecuteInfo executeInfo) {
        return JobBuilder
                .newJob(ExecuteJob.class)
                .withIdentity(getJobKey(executeInfo))
                .setJobData(getJobDataMap(executeInfo))
                .storeDurably(false)
                .requestRecovery()
                .build();
    }

    private Trigger getTrigger(ExecuteInfo executeInfo) {
        return TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(executeInfo))
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(5)
//                        .withRepeatCount(executeInfo.getFourTuple().getNodeId().equals("ffffffff") ? 0 : 10)
                )
                .startNow()
                .build();
    }

    private JobDataMap getJobDataMap(ExecuteInfo executeInfo) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(ExecuteJob.EXECUTE_INFO, executeInfo);
        return jobDataMap;
    }

    private TriggerKey getTriggerKey(ExecuteInfo executeInfo) {
        FourTuple fourTuple = executeInfo.getFourTuple();
        return TriggerKey.triggerKey(fourTuple.getNodeId(), fourTuple.getSerialNo() + "-" + fourTuple.getVersion() + "-" + fourTuple.getRunVersion());
    }

    private JobKey getJobKey(ExecuteInfo executeInfo) {
        FourTuple fourTuple = executeInfo.getFourTuple();
        return JobKey.jobKey(fourTuple.getNodeId(), fourTuple.getSerialNo() + "-" + fourTuple.getVersion() + "-" + fourTuple.getRunVersion());
    }
}
