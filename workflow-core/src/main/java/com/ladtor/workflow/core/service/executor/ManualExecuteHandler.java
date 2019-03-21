package com.ladtor.workflow.core.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.core.bo.WorkFlowBo;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;
import com.ladtor.workflow.core.bo.execute.ManualExecuteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManualExecuteHandler extends AbstractExecutorHandler<ManualExecuteInfo> {

    public static final String NAME = "MANUAL";

    @Autowired
    private Executor executor;

    public ManualExecuteHandler() {
        super(NAME);
    }

    public ManualExecuteHandler(String name) {
        super(name);
    }

    @Override
    protected void doExecute(ManualExecuteInfo executeInfo) {
        FourTuple fourTuple = executeInfo.getFourTuple();
        WorkFlowBo workFlow = getWorkFlow(fourTuple);
        JSONObject result = collectSourceResult(workFlow, fourTuple);
        result.putAll(executeInfo.getParams());
        ExecuteResult executeResult = buildExecuteResult(executeInfo, result);
        executor.success(executeResult);
    }

    @Override
    protected void doSuccess(ExecuteResult executeResult) {

    }

    @Override
    protected void doFail(ExecuteResult executeResult) {

    }

    @Override
    protected boolean doCancel(ManualExecuteInfo executeInfo) {
        return false;
    }
}
