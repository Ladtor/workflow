package com.ladtor.workflow.core.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.common.constant.StatusEnum;
import com.ladtor.workflow.core.bo.WorkFlowBo;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;
import com.ladtor.workflow.core.bo.execute.ResultExecuteInfo;
import com.ladtor.workflow.core.service.wrapper.ExecuteLogWrapper;
import com.ladtor.workflow.core.service.wrapper.NodeLogWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/13 15:20
 */
@Service
public class ResultExecuteHandler extends AbstractExecutorHandler<ResultExecuteInfo> {

    public static final String NAME = "RESULT";

    @Autowired
    private Executor executor;

    @Autowired
    private ExecuteLogWrapper executeLogWrapper;

    @Autowired
    private NodeLogWrapper nodeLogWrapper;

    public ResultExecuteHandler() {
        super(NAME);
    }

    public ResultExecuteHandler(String name) {
        super(name);
    }

    @Override
    protected void doExecute(ResultExecuteInfo executeInfo) {
        WorkFlowBo workFlow = getWorkFlow(executeInfo);
        FourTuple fourTuple = executeInfo.getFourTuple();
        if (sourceSuccess(workFlow, fourTuple)) {
            JSONObject result = collectSourceResult(workFlow, fourTuple);
            ExecuteResult executeResult = buildExecuteResult(executeInfo, result);
            executor.success(executeResult);
        } else {
            nodeLogWrapper.updateStatus(fourTuple, StatusEnum.BLOCK);
        }
    }

    @Override
    protected void doSuccess(ExecuteResult executeResult) {
        executeLogWrapper.success(executeResult.getFourTuple(), executeResult.getResult());
    }

    @Override
    protected void doFail(ExecuteResult executeResult) {

    }

    @Override
    protected boolean doCancel(ResultExecuteInfo executeInfo) {
        return false;
    }
}
