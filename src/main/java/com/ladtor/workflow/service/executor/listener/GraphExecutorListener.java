package com.ladtor.workflow.service.executor.listener;

import com.alibaba.fastjson.JSON;
import com.ladtor.workflow.bo.Edge;
import com.ladtor.workflow.bo.GraphBo;
import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.bo.WorkFlowBo;
import com.ladtor.workflow.bo.domain.NodeLog;
import com.ladtor.workflow.bo.execute.ExecuteInfo;
import com.ladtor.workflow.bo.execute.ExecuteResult;
import com.ladtor.workflow.bo.execute.FourTuple;
import com.ladtor.workflow.constant.NodeType;
import com.ladtor.workflow.service.NodeLogService;
import com.ladtor.workflow.service.WorkFlowService;
import com.ladtor.workflow.service.chain.NodeCheckHandler;
import com.ladtor.workflow.service.executor.Executor;
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
    private WorkFlowService workFlowService;

    @Autowired
    private NodeLogService nodeLogService;

    @Autowired
    private Executor executor;

    @Autowired
    private NodeCheckHandler nodeCheckHandler;

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

        WorkFlowBo workFlow = workFlowService.getWorkFlow(fourTuple.getSerialNo());
        GraphBo graph = workFlow.getGraph();
        Node currentNode = graph.getNode(fourTuple.getNodeId());
        List<Edge> sourceEdges = graph.getSourceEdges(currentNode);
        for (Edge sourceEdge : sourceEdges) {
            if(sourceEdge.run(fourTuple, executeResult.getResult())){
                Node targetNode = graph.getTargetNode(sourceEdge);
                ExecuteInfo targetNodeExecuteInfo = targetNode.getExecuteInfo(fourTuple);
                targetNodeExecuteInfo.getParams().putAll(executeResult.getResult());
                if(canRun(targetNode, targetNodeExecuteInfo)){
                    executor.execute(targetNodeExecuteInfo);
                }
            }
        }
    }

    private boolean canRun(Node node, ExecuteInfo executeInfo) {
        if (node.getNodeType().equals(NodeType.AND) || node.getNodeType().equals(NodeType.RESULT)) {
            return true;
        }
        return nodeCheckHandler.canRun(new FourTuple(executeInfo.getFourTuple(), node.getId()));
    }

    @Override
    public void fail(String executorName, ExecuteResult executeResult) {

    }

    @Override
    public void cancel(String executorName, ExecuteInfo executeInfo) {

    }

}
