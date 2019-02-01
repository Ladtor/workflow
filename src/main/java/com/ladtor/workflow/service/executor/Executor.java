package com.ladtor.workflow.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.GraphBo;
import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.bo.WorkFlowBo;
import com.ladtor.workflow.bo.execute.ExecuteInfo;
import com.ladtor.workflow.bo.execute.ExecuteResult;
import com.ladtor.workflow.bo.execute.StartExecuteInfo;
import com.ladtor.workflow.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/12 21:24
 */
@Service
public class Executor implements ExecutorHandler<ExecuteInfo> {
    public static final String PARENT_KEY = "parent";

    @Autowired
    private WorkFlowService workFlowService;

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
        ExecutorHandler executorHandler = ExecutorFactory.getExecutorHandler(executeInfo.getNodeType());
        if (executorHandler != null) {
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
}
