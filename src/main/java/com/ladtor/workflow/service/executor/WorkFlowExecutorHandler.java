package com.ladtor.workflow.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.execute.ExecuteResult;
import com.ladtor.workflow.bo.execute.WorkFlowExecuteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/12 16:42
 */
@Service
class WorkFlowExecutorHandler extends AbstractExecutorHandler<WorkFlowExecuteInfo> {
    private static final String NAME = "WORK_FLOW";

    @Autowired
    private Executor executor;

    public WorkFlowExecutorHandler() {
        super(NAME);
    }

    public WorkFlowExecutorHandler(String name) {
        super(name);
    }

    @Override
    protected void preExecute(WorkFlowExecuteInfo executeInfo) {
        super.preExecute(executeInfo);
        JSONObject params = executeInfo.getParams();
        params.put(Executor.PARENT_KEY, executeInfo.getFourTuple());
    }

    @Override
    protected void doExecute(WorkFlowExecuteInfo executeInfo) {
        String subSerialNo = executeInfo.getSubSerialNo();
        executor.execute(subSerialNo, executeInfo.getParams());
    }

    @Override
    protected void doSuccess(ExecuteResult executeResult) {

    }

    @Override
    protected void doFail(ExecuteResult executeResult) {

    }

    @Override
    protected boolean doCancel(WorkFlowExecuteInfo executeInfo) {
        return false;
    }
}
