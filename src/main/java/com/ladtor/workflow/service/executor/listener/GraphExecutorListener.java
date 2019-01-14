package com.ladtor.workflow.service.executor.listener;

import com.ladtor.workflow.bo.Edge;
import com.ladtor.workflow.bo.GraphBo;
import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.bo.WorkFlowBo;
import com.ladtor.workflow.bo.execute.ExecuteInfo;
import com.ladtor.workflow.service.WorkFlowService;
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
    private Executor executor;

    @Override
    public void before(String executorName, ExecuteInfo executeInfo) {

    }

    @Override
    public void complete(String executorName, ExecuteInfo executeInfo) {

    }

    @Override
    public void success(String executorName, ExecuteInfo executeInfo) {
        WorkFlowBo workFlow = workFlowService.getWorkFlow(executeInfo.getSerialNo(), executeInfo.getVersion());
        GraphBo graph = workFlow.getGraph();
        Node currentNode = executeInfo.getNode();
        List<Edge> sourceEdges = graph.getSourceEdges(currentNode);
        for (Edge sourceEdge : sourceEdges) {
            if(sourceEdge.valid(executeInfo.getResult())){
                Node targetNode = graph.getTargetNode(sourceEdge);
                ExecuteInfo targetNodeExecuteInfo = targetNode.getExecuteInfo(executeInfo.getRunVersion());
                if(canRun(targetNode, targetNodeExecuteInfo)){
                    executor.execute(targetNodeExecuteInfo);
                }
            }
        }
    }

    private boolean canRun(Node targetNode, ExecuteInfo targetNodeExecuteInfo) {
        return true;
    }

    @Override
    public void fail(String executorName, ExecuteInfo executeInfo) {

    }

    @Override
    public void cancel(String executorName, ExecuteInfo executeInfo) {

    }

}
