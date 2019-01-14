package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.bo.Edge;
import com.ladtor.workflow.bo.GraphBo;
import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.bo.WorkFlowBo;
import com.ladtor.workflow.bo.execute.AndExecuteInfo;
import com.ladtor.workflow.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/13 22:48
 */
@Service
public class AndExecuteHandler extends AbstractExecutorHandler<AndExecuteInfo> {

    public static final String NAME = "AND";

    @Autowired
    private WorkFlowService workFlowService;
    private Executor executor;

    public AndExecuteHandler() {
        super(NAME);
    }

    public AndExecuteHandler(String name) {
        super(name);
    }

    @Override
    protected void doExecute(AndExecuteInfo executeInfo) {
        WorkFlowBo workFlow = workFlowService.getWorkFlow(executeInfo.getSerialNo(), executeInfo.getVersion());
        GraphBo graph = workFlow.getGraph();
        List<Edge> targetEdges = graph.getTargetEdges(executeInfo.getNode());
        for (Edge targetEdge : targetEdges) {
            Node sourceNode = graph.getSourceNode(targetEdge);
            Integer runVersion = executeInfo.getRunVersion();
            if(!(sourceNode.isReady(runVersion)
                    && targetEdge.valid(sourceNode.getExecuteInfo(runVersion).getResult()))){
                    return;
            }
        }
        executor.success(executeInfo);
    }

    @Override
    protected void doSuccess(AndExecuteInfo executeInfo) {

    }

    @Override
    protected void doFail(AndExecuteInfo executeInfo) {

    }

    @Override
    protected boolean doCancel(AndExecuteInfo executeInfo) {
        return false;
    }
}
