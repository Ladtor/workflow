package com.ladtor.workflow.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.Edge;
import com.ladtor.workflow.bo.GraphBo;
import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.bo.WorkFlowBo;
import com.ladtor.workflow.bo.execute.ExecuteInfo;
import com.ladtor.workflow.bo.execute.ExecuteResult;
import com.ladtor.workflow.bo.execute.FourTuple;
import com.ladtor.workflow.bo.execute.TwoTuple;
import com.ladtor.workflow.constant.StatusEnum;
import com.ladtor.workflow.service.NodeLogService;
import com.ladtor.workflow.service.WorkFlowService;
import com.ladtor.workflow.service.executor.listener.ExecutorListener;
import com.ladtor.workflow.service.executor.listener.ExecutorListenerHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/12 16:56
 */
public abstract class AbstractExecutorHandler<T extends ExecuteInfo> implements ExecutorHandler<T> {
    private String name;

    @Autowired
    private ExecutorListenerHandler executorListenerHandler;

    @Autowired
    private NodeLogService nodeLogService;

    @Autowired
    private WorkFlowService workFlowService;

    public AbstractExecutorHandler(String name) {
        this.name = name;
    }

    protected abstract void doExecute(T executeInfo);

    protected abstract void doSuccess(ExecuteResult executeResult);

    protected abstract void doFail(ExecuteResult executeResult);

    protected abstract boolean doCancel(T executeInfo);

    protected void preExecute(T executeInfo) {
    }

    @Override
    public void success(ExecuteResult executeResult) {
        nodeLogService.saveOrUpdate(executeResult.getFourTuple(), StatusEnum.SUCCESS, null, executeResult.getResult());
        doSuccess(executeResult);
        List<ExecutorListener> listeners = executorListenerHandler.getListeners();
        listeners.forEach(listener -> listener.success(name, executeResult));
        listeners.forEach(listener -> listener.complete(name, executeResult));
    }

    @Override
    public void fail(ExecuteResult executeResult) {
        nodeLogService.saveOrUpdate(executeResult.getFourTuple(), StatusEnum.FAIL, null, executeResult.getResult());
        doFail(executeResult);
        List<ExecutorListener> listeners = executorListenerHandler.getListeners();
        listeners.forEach(listener -> listener.fail(name, executeResult));
        listeners.forEach(listener -> listener.complete(name, executeResult));
    }

    @Override
    public final void execute(T executeInfo) {
        preExecute(executeInfo);
        JSONObject params = executeInfo.getParams();
        nodeLogService.saveOrUpdate(executeInfo.getFourTuple(), StatusEnum.RUNNING, params, null);
        List<ExecutorListener> listeners = executorListenerHandler.getListeners();
        listeners.forEach(listener -> listener.before(name, executeInfo));
        doExecute(executeInfo);
    }

    @Override
    public final boolean cancel(T executeInfo) {
        boolean result = doCancel(executeInfo);
        if (result) {
            nodeLogService.updateStatus(executeInfo.getFourTuple(), StatusEnum.CANCEL);
            List<ExecutorListener> listeners = executorListenerHandler.getListeners();
            listeners.forEach(listener -> listener.cancel(name, executeInfo));
        }
        return result;
    }

    JSONObject collectSourceResult(WorkFlowBo workFlow, FourTuple fourTuple) {
        JSONObject result = new JSONObject();
        GraphBo graph = workFlow.getGraph();
        Node node = graph.getNode(fourTuple.getNodeId());
        List<Edge> targetEdges = graph.getTargetEdges(node);
        for (Edge targetEdge : targetEdges) {
            Node sourceNode = graph.getSourceNode(targetEdge);
            JSONObject nodeExecuteResult = sourceNode.getExecuteResult(fourTuple);
            result.putAll(nodeExecuteResult);
        }
        return result;
    }

    boolean sourceSuccess(WorkFlowBo workFlow, FourTuple fourTuple) {
        GraphBo graph = workFlow.getGraph();
        Node node = graph.getNode(fourTuple.getNodeId());
        List<Edge> targetEdges = graph.getTargetEdges(node);
        for (Edge targetEdge : targetEdges) {
            Node sourceNode = graph.getSourceNode(targetEdge);
            if (sourceNode.isReady(fourTuple)) {
                JSONObject nodeExecuteResult = sourceNode.getExecuteResult(fourTuple);
                if (targetEdge.run(fourTuple, nodeExecuteResult)) {
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    ExecuteResult buildExecuteResult(T executeInfo, JSONObject result) {
        return ExecuteResult.builder()
                .fourTuple(executeInfo.getFourTuple())
                .nodeType(executeInfo.getNodeType())
                .result(result)
                .build();
    }

    WorkFlowBo getWorkFlow(T executeInfo) {
        return getWorkFlow(executeInfo.getFourTuple());
    }

    WorkFlowBo getWorkFlow(TwoTuple twoTuple) {
        return getWorkFlow(twoTuple.getSerialNo(), twoTuple.getVersion());
    }

    WorkFlowBo getWorkFlow(String serialNo, Integer version) {
        return workFlowService.getWorkFlow(serialNo, version);
    }
}
