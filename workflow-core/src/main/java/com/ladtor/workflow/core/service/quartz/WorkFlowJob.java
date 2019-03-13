package com.ladtor.workflow.core.service.quartz;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.core.service.executor.Executor;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkFlowJob implements Job {

    @Autowired
    private Executor executor;

    @Override
    public void execute(JobExecutionContext context) {
        String serialNo = context.getJobDetail().getKey().getName();
        JobDataMap params = context.getTrigger().getJobDataMap();
        executor.execute(serialNo, new JSONObject(params));
    }
}
