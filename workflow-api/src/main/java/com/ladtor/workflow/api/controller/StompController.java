package com.ladtor.workflow.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.core.bo.Node;
import com.ladtor.workflow.core.bo.execute.ExecuteInfo;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;
import com.ladtor.workflow.core.service.executor.Executor;
import com.ladtor.workflow.core.service.executor.ExecutorFactory;
import com.ladtor.workflow.core.service.wrapper.WorkFlowWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {
    @Autowired
    private Executor executor;

    @Autowired
    private WorkFlowWrapper workFlowWrapper;

    @MessageMapping(value = "/execute/{serialNo}/{version}/{runVersion}/{nodeId}")
    public void execute(@DestinationVariable String serialNo, @DestinationVariable Integer version, @DestinationVariable Integer runVersion, @DestinationVariable String nodeId, JSONObject params) {
        ExecuteInfo executeInfo = ExecutorFactory.getExecuteInfo(new FourTuple(serialNo, version, runVersion, nodeId));
        executeInfo.setParams(params);
        executor.execute(executeInfo, false);
    }

    @MessageMapping(value = "/success/{serialNo}/{version}/{runVersion}/{nodeId}")
    public void success(@DestinationVariable String serialNo, @DestinationVariable Integer version, @DestinationVariable Integer runVersion, @DestinationVariable String nodeId, JSONObject result) {
        FourTuple fourTuple = new FourTuple(serialNo, version, runVersion, nodeId);
        Node node = workFlowWrapper.getNode(fourTuple);
        executor.success(ExecuteResult.builder()
                .nodeType(node.getNodeType())
                .fourTuple(fourTuple)
                .result(result)
                .build());
    }

    @MessageMapping(value = "/fail/{serialNo}/{version}/{runVersion}/{nodeId}")
    public void fail(@DestinationVariable String serialNo, @DestinationVariable Integer version, @DestinationVariable Integer runVersion, @DestinationVariable String nodeId, JSONObject result) {
        FourTuple fourTuple = new FourTuple(serialNo, version, runVersion, nodeId);
        Node node = workFlowWrapper.getNode(fourTuple);
        executor.fail(ExecuteResult.builder()
                .nodeType(node.getNodeType())
                .fourTuple(fourTuple)
                .result(result)
                .build());
    }

}
