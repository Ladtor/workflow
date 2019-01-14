package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.WorkflowApplicationTests;
import com.ladtor.workflow.bo.execute.ExecuteInfo;
import com.ladtor.workflow.bo.execute.WorkFlowExecuteInfo;
import com.ladtor.workflow.constant.NodeType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author liudongrong
 * @date 2019/1/13 11:40
 */
public class ExecutorTest extends WorkflowApplicationTests {

    @Autowired
    private Executor executor;

    @Test
    public void execute() {
        ExecuteInfo executeInfo = new WorkFlowExecuteInfo();
        executeInfo.setNodeType(NodeType.WORK_FLOW);
        executeInfo.setVersion(1);
        executeInfo.setSerialNo(UUID.randomUUID().toString());
        executeInfo.setRunVersion(2);
        executor.execute(executeInfo);
    }
}