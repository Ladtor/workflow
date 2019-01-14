package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.bo.GraphBo;
import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.bo.WorkFlowBo;
import com.ladtor.workflow.bo.execute.WorkFlowExecuteInfo;
import com.ladtor.workflow.service.WorkFlowService;
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
    private WorkFlowService workFlowService;

    @Autowired
    private Executor executor;

    public WorkFlowExecutorHandler() {
        super(NAME);
    }

    public WorkFlowExecutorHandler(String name) {
        super(name);
    }

    @Override
    protected void doExecute(WorkFlowExecuteInfo executeInfo) {
        WorkFlowBo workFlow = workFlowService.getWorkFlow(executeInfo.getSerialNo(), executeInfo.getVersion());
        GraphBo graph = workFlow.getGraph();
        Node startNode = graph.getStartNode();

        Integer runVersion = workFlow.createRunVersion();

        executor.execute(graph.getExecuteInfo(startNode, runVersion));
    }

    @Override
    protected void doSuccess(WorkFlowExecuteInfo executeInfo) {

    }

    @Override
    protected void doFail(WorkFlowExecuteInfo executeInfo) {

    }

    @Override
    protected boolean doCancel(WorkFlowExecuteInfo executeInfo) {
        return false;
    }
}
