package com.ladtor.workflow.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.WorkFlowBo;
import com.ladtor.workflow.bo.execute.AndExecuteInfo;
import com.ladtor.workflow.bo.execute.ExecuteResult;
import com.ladtor.workflow.bo.execute.FourTuple;
import com.ladtor.workflow.constant.StatusEnum;
import com.ladtor.workflow.service.NodeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/13 22:48
 */
@Service
public class AndExecuteHandler extends AbstractExecutorHandler<AndExecuteInfo> {

    public static final String NAME = "AND";

    @Autowired
    private Executor executor;

    @Autowired
    private NodeLogService nodeLogService;

    public AndExecuteHandler() {
        super(NAME);
    }

    public AndExecuteHandler(String name) {
        super(name);
    }

    @Override
    protected void doExecute(AndExecuteInfo executeInfo) {
        FourTuple fourTuple = executeInfo.getFourTuple();
        WorkFlowBo workFlow = getWorkFlow(fourTuple);
        if (!sourceSuccess(workFlow, fourTuple)) {
            nodeLogService.updateStatus(fourTuple, StatusEnum.BLOCK);
            return;
        }
        JSONObject result = collectSourceResult(workFlow, fourTuple);
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
    protected boolean doCancel(AndExecuteInfo executeInfo) {
        return false;
    }
}
