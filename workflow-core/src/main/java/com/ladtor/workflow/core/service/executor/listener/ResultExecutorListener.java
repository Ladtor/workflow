package com.ladtor.workflow.core.service.executor.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.common.constant.NodeType;
import com.ladtor.workflow.dao.domain.ExecuteLog;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.dao.ExecuteLogService;
import com.ladtor.workflow.core.service.executor.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/31 20:05
 */
@Service
public class ResultExecutorListener extends AbstractExecutorListener {

    @Autowired
    private ExecuteLogService executeLogService;

    @Autowired
    private Executor executor;

    @Override
    public void success(String executorName, ExecuteResult executeResult) {
        if (!NodeType.RESULT.equals(executeResult.getNodeType())) {
            return;
        }
        ExecuteResult result = getExecuteResult(executeResult);
        if (result != null) {
            executor.success(result);
        }
    }

    @Override
    public void fail(String executorName, ExecuteResult executeResult) {
        if (!NodeType.RESULT.equals(executeResult.getNodeType())) {
            return;
        }
        ExecuteResult result = getExecuteResult(executeResult);
        if (result != null) {
            executor.fail(result);
        }
    }

    private ExecuteResult getExecuteResult(ExecuteResult executeResult) {
        ExecuteLog executeLog = executeLogService.get(executeResult.getFourTuple());
        String params = executeLog.getParams();
        if (params != null) {
            JSONObject jsonObject = JSON.parseObject(params);
            FourTuple fourTuple = jsonObject.getObject(Executor.PARENT_KEY, FourTuple.class);
            if (fourTuple != null) {
                return ExecuteResult.builder()
                        .fourTuple(fourTuple)
                        .result(executeResult.getResult())
                        .nodeType(NodeType.WORK_FLOW)
                        .build();
            }
        }
        return null;
    }
}
