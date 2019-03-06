package com.ladtor.workflow.core.service.executor.listener;

import com.alibaba.fastjson.JSON;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.core.bo.Edge;
import com.ladtor.workflow.core.bo.GraphBo;
import com.ladtor.workflow.core.bo.Node;
import com.ladtor.workflow.core.bo.WorkFlowBo;
import com.ladtor.workflow.core.bo.execute.ExecuteInfo;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;
import com.ladtor.workflow.core.service.executor.Executor;
import com.ladtor.workflow.core.service.wrapper.WorkFlowWrapper;
import com.ladtor.workflow.dao.NodeLogService;
import com.ladtor.workflow.dao.domain.NodeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/13 15:39
 */
@Service
public class GraphExecutorListener implements ExecutorListener {
    @Autowired
    private WorkFlowWrapper workFlowWrapper;

    @Autowired
    private NodeLogService nodeLogService;

    @Autowired
    private Executor executor;

    @Override
    public void before(String executorName, ExecuteInfo executeInfo) {

    }

    @Override
    public void complete(String executorName, ExecuteResult executeResult) {

    }

    @Override
    public void success(String executorName, ExecuteResult executeResult) {
        FourTuple fourTuple = executeResult.getFourTuple();
        NodeLog nodeLog = nodeLogService.get(fourTuple);
        executeResult.getResult().putAll(JSON.parseObject(nodeLog.getResult()));

        WorkFlowBo workFlow = workFlowWrapper.getWorkFlow(fourTuple.getSerialNo());
        GraphBo graph = workFlow.getGraph();
        Node currentNode = graph.getNode(fourTuple.getNodeId());
        List<Edge> sourceEdges = graph.getSourceEdges(currentNode);
        for (Edge sourceEdge : sourceEdges) {
            if(sourceEdge.run(fourTuple, executeResult.getResult())){
                Node targetNode = graph.getTargetNode(sourceEdge);
                ExecuteInfo targetNodeExecuteInfo = targetNode.getExecuteInfo(fourTuple);
                targetNodeExecuteInfo.getParams().putAll(executeResult.getResult());
                executor.execute(targetNodeExecuteInfo);
            }
        }
    }

    @Override
    public void fail(String executorName, ExecuteResult executeResult) {

    }

    @Override
    public void cancel(String executorName, ExecuteInfo executeInfo) {

    }

}
